package projectbuildup.saver.domain.dto.res;

import lombok.*;
import projectbuildup.saver.domain.challenge.entity.Challenge;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@ToString
public class GetChallengeResponseDto {
    private Long challengeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String mainTitle;
    private String subTitle;
    private String content;
    private Long savingAmount;
    private Long joinedNumber;

    public GetChallengeResponseDto(Challenge challenge) {
        this.challengeId = challenge.getId();
        this.startDate = challenge.getStartDate();
        this.endDate = challenge.getEndDate();
        this.mainTitle = challenge.getMainTitle();
        this.subTitle = challenge.getSubTitle();
        this.content = challenge.getContent();
        this.savingAmount = challenge.getSavingAmount();
        this.joinedNumber = (long) challenge.getParticipationList().size();
    }
}
