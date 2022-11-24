package projectbuildup.saver.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.saver.domain.dto.req.CreateUserReqDto;
import projectbuildup.saver.domain.dto.res.GetUserResDto;
import projectbuildup.saver.domain.image.dto.ImageDto;
import projectbuildup.saver.domain.image.entity.ImageEntity;
import projectbuildup.saver.domain.image.repository.ImageRepository;
import projectbuildup.saver.domain.image.service.ImageService;
import projectbuildup.saver.domain.user.dto.PasswordUpdateParam;
import projectbuildup.saver.domain.user.dto.ProfileUpdateParam;
import projectbuildup.saver.domain.user.entity.UserEntity;
import projectbuildup.saver.domain.user.error.exception.CUserExistException;
import projectbuildup.saver.domain.user.error.exception.CUserNotFoundException;
import projectbuildup.saver.domain.user.repository.UserRepository;
import projectbuildup.saver.domain.user.service.interfaces.UserService;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final ImageService imageService;
    private final ImageRepository imageRepository;

    @Override
    public void createUser(CreateUserReqDto createUserReqDto) {
        UserEntity userEntity = UserEntity.builder()
                .loginId(createUserReqDto.getLoginId())
                .password(createUserReqDto.getPassword())
                .nickName(createUserReqDto.getNickName())
                .phoneNumber(createUserReqDto.getPhoneNumber())
                .build();

        userRepository.save(userEntity);
    }

    @Override
    public GetUserResDto getUser(String loginId) {
        UserEntity user = userRepository.findByLoginId(loginId).orElseThrow(CUserNotFoundException::new);
        GetUserResDto getUserResDto = GetUserResDto
                .builder()
                .nickname(user.getNickName())
                .build();
        return getUserResDto;
    }

    @Override
    public void updateUser(String loginId, String nickname) {
        UserEntity user = userRepository.findByLoginId(loginId).orElseThrow(CUserExistException::new);
        user.setNickName(nickname);
        userRepository.save(user);
    }

    /**
     * 사용자의 비밀번호를 변경합니다.
     * @param loginId 사용자 아이디
     * @param passwordUpdateParam 비밀번호
     */
    @Override
    public void changePassword(String loginId, PasswordUpdateParam passwordUpdateParam) {
        UserEntity user = userRepository.findByLoginId(loginId).orElseThrow(CUserNotFoundException::new);
        user.setPassword(passwordEncoder.encode(passwordUpdateParam.getPassword()));
        userRepository.save(user);
    }

    /**
     * 사용자의 프로필을 수정합니다.
     * @param loginId 사용자 아이디
     * @param profileUpdateParam 수정 항목 (닉네임)
     * @return 수정한 사용자의 아이디
     */
    @Override
    public Long updateProfile(String loginId, ProfileUpdateParam profileUpdateParam) {
        UserEntity user = userRepository.findByLoginId(loginId).orElseThrow(CUserNotFoundException::new);
        user.setNickName(profileUpdateParam.getNickName());
        userRepository.save(user);
        return user.getId();
    }

    /**
     * 프로필 사진을 변경합니다.
     * @param loginId 사용자 아이디
     * @param imageFile 변경할 사진 파일
     * @return 변경한 사용자의 아이디
     */
    @Override
    public Long changeProfileImage(String loginId, MultipartFile imageFile) {
        UserEntity user = userRepository.findByLoginId(loginId).orElseThrow(CUserNotFoundException::new);
        ImageDto imageDto = imageService.uploadImage(imageFile);
        ImageEntity image = imageDto.toEntity();
        imageRepository.save(image);
        imageService.removeImage(user.getProfileImage());
        user.setProfileImage(image);
        userRepository.save(user);

        return user.getId();
    }

}
