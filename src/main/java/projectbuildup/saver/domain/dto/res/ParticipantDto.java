package projectbuildup.saver.domain.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.saver.domain.user.entity.Member;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantDto {
    private String idToken;
    private String nickName;
//    private Long recentRanking;
    private Long totalAmount;

    public ParticipantDto(Member member, Long totalAmount){
        this.idToken =  member.getIdToken();
        this.nickName = member.getNickName();
        this.totalAmount = totalAmount;
    }
}
