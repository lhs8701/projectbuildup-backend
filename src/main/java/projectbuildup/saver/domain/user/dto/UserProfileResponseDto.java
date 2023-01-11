package projectbuildup.saver.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.saver.domain.user.entity.Member;

@NoArgsConstructor
@Getter
public class UserProfileResponseDto {
    String nickName;
    String profileImageUrl;


    public UserProfileResponseDto(Member member){
        this.nickName = member.getNickName();
        this.profileImageUrl = member.getProfileImage().getFileUrl();
    }
}
