package projectbuildup.saver.domain.user.service.interfaces;

import org.springframework.web.multipart.MultipartFile;
import projectbuildup.saver.domain.dto.req.CreateUserReqDto;
import projectbuildup.saver.domain.dto.res.GetUserResDto;
import projectbuildup.saver.domain.user.dto.PasswordUpdateParam;
import projectbuildup.saver.domain.user.dto.ProfileUpdateParam;
import projectbuildup.saver.domain.user.entity.UserEntity;

public interface UserService {

    void createUser(CreateUserReqDto createUserReqDto);
    GetUserResDto getUser(String loginId);
    void updateUser(String loginId, String nickname);

    void changePassword(PasswordUpdateParam passwordUpdateParam, UserEntity user);

    Long updateProfile(ProfileUpdateParam profileUpdateParam, UserEntity user);

    Long changeProfileImage(MultipartFile imageFile, UserEntity user);
}
