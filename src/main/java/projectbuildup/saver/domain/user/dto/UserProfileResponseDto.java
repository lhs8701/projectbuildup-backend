package projectbuildup.saver.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.saver.domain.user.entity.UserEntity;

@NoArgsConstructor
@Getter
public class UserProfileResponseDto {
    String nickName;
    String profileImageUrl;


    public UserProfileResponseDto(UserEntity user){
        this.nickName = user.getNickName();
        this.profileImageUrl = user.getProfileImage().getFileUrl();
    }
}
