package projectbuildup.saver.domain.dto.res;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
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
