package projectbuildup.saver.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
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
     * @param passwordUpdateParam 비밀번호
     * @param user 사용자
     */
    @Override
    public void changePassword(PasswordUpdateParam passwordUpdateParam, UserEntity user) {
        user.setPassword(passwordEncoder.encode(passwordUpdateParam.getPassword()));
    }

    /**
     * 사용자의 프로필을 수정합니다.
     * @param profileUpdateParam 수정 항목 (닉네임)
     * @param user 사용자
     * @return 수정한 사용자의 아이디
     */
    @Override
    public Long updateProfile(ProfileUpdateParam profileUpdateParam, UserEntity user) {
        user.setNickName(profileUpdateParam.getNickName());
        return user.getId();
    }

    @Override
    public Long updateProfileImage(MultipartFile imageFile, UserEntity user) {
        ImageDto imageDto = imageService.uploadImage(imageFile);
        ImageEntity image = imageDto.toEntity();
        imageRepository.save(image);


        return null;
    }

}
