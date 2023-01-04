package projectbuildup.saver.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.saver.domain.user.entity.User;

@NoArgsConstructor
@Getter
public class UserProfileResponseDto {
    String nickName;
    String profileImageUrl;


    public UserProfileResponseDto(User user){
        this.nickName = user.getNickName();
        this.profileImageUrl = user.getProfileImage().getFileUrl();
    }
}
