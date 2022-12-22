package projectbuildup.saver.domain.user.service.interfaces;

import org.springframework.web.multipart.MultipartFile;
import projectbuildup.saver.domain.user.dto.PasswordUpdateParam;
import projectbuildup.saver.domain.user.dto.ProfileUpdateParam;
import projectbuildup.saver.domain.user.dto.UserIdRequestParam;
import projectbuildup.saver.domain.user.dto.UserProfileResponseDto;

public interface UserService {

    void changePassword(PasswordUpdateParam passwordUpdateParam);

    Long updateProfile(ProfileUpdateParam profileUpdateParam);

    Long changeProfileImage(UserIdRequestParam userIdRequestParam, MultipartFile imageFile);

    UserProfileResponseDto getProfile(UserIdRequestParam userIdRequestParam);
}
