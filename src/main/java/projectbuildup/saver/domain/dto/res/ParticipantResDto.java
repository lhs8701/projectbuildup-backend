package projectbuildup.saver.domain.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantResDto {
    private String loginId;
    private String nickName;
    private Long recentRanking;
    private Long savingAmount;
}
