package projectbuildup.saver.domain.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetChallengeResDto {
    private Long challengeId;
    private String startDate;
    private String endDate;
    private String mainTitle;
    private String subTitle;
    private String content;
    private Long savingAmount;
    private Long population;
}
