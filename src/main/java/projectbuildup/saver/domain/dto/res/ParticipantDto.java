package projectbuildup.saver.domain.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.saver.domain.user.entity.User;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantDto {
    private String idToken;
    private String nickName;
//    private Long recentRanking;
    private Long totalAmount;

    public ParticipantDto(User user, Long totalAmount){
        this.idToken =  user.getIdToken();
        this.nickName = user.getNickName();
        this.totalAmount = totalAmount;
    }
}
