package projectbuildup.saver.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.saver.domain.auth.basic.dto.SignupRequestDto;
import projectbuildup.saver.domain.image.dto.ImageDto;
import projectbuildup.saver.domain.image.entity.Image;
import projectbuildup.saver.domain.image.repository.ImageRepository;
import projectbuildup.saver.domain.image.service.ImageService;
import projectbuildup.saver.domain.user.dto.PasswordUpdateParam;
import projectbuildup.saver.domain.user.dto.ProfileUpdateParam;
import projectbuildup.saver.domain.user.dto.UserIdRequestParam;
import projectbuildup.saver.domain.user.dto.UserProfileResponseDto;
import projectbuildup.saver.domain.user.entity.User;
import projectbuildup.saver.domain.user.repository.UserJpaRepository;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private final UserFindService userFindService;
    private final PasswordEncoder passwordEncoder;

    private final ImageService imageService;
    private final ImageRepository imageRepository;

    /**
     * 사용자의 비밀번호를 변경합니다.
     * @param passwordUpdateParam 변경할 유저 아이디, 변경할 비밀번호
     */
    public void changePassword(PasswordUpdateParam passwordUpdateParam) {
        User user = userFindService.findByIdToken(passwordUpdateParam.getIdToken());
        user.setPassword(passwordEncoder.encode(passwordUpdateParam.getPassword()));
        userJpaRepository.save(user);
    }


    /**
     * 사용자의 프로필을 조회합니다.
     * @param userIdRequestParam 유저 아이디
     * @return 유저 프로필(닉네임, 프로필 사진 URL)
     */
    public UserProfileResponseDto getProfile(UserIdRequestParam userIdRequestParam) {
        User user = userFindService.findByIdToken(userIdRequestParam.getIdToken());
        return new UserProfileResponseDto(user);
    }


    /**
     * 사용자의 프로필을 수정합니다.
     * @param profileUpdateParam 수정 항목 (수정할 유저아이디, 닉네임)
     * @return 수정한 사용자의 아이디
     */
    public Long updateProfile(ProfileUpdateParam profileUpdateParam) {
        User user = userFindService.findByIdToken(profileUpdateParam.getIdToken());
        user.setNickName(profileUpdateParam.getNickName());
        userJpaRepository.save(user);
        return user.getId();
    }


    /**
     * 프로필 사진을 변경합니다.
     * @param userIdRequestParam 사용자 아이디
     * @param imageFile 변경할 사진 파일
     * @return 변경한 사용자의 아이디
     */
    public Long changeProfileImage(UserIdRequestParam userIdRequestParam, MultipartFile imageFile) {
        User user = userFindService.findByIdToken(userIdRequestParam.getIdToken());
        ImageDto imageDto = imageService.uploadImage(imageFile);
        Image image = imageDto.toEntity();
        imageRepository.save(image);
        imageService.removeImage(user.getProfileImage());
        user.setProfileImage(image);
        userJpaRepository.save(user);

        return user.getId();
    }

    /**
     * 회원가입 시, 회원을 생성합니다.
     * @param signupRequestDto 회원가입 시 받는 필드 (아이디토큰, 비밀번호, 닉네임, 전화번호)
     * @return 유저 아이디
     */
    public Long createUserBySignUp(SignupRequestDto signupRequestDto){
        User user = signupRequestDto.toEntity(passwordEncoder);
        return userJpaRepository.save(user).getId();
    }

    /** 회원을 삭제합니다.
     * @param userId 유저 아이디
     */
    public void deleteById(Long userId){
        userJpaRepository.deleteById(userId);
    }
}
