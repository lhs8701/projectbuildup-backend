package projectbuildup.saver.domain.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.saver.domain.challenge.entity.Challenge;
import projectbuildup.saver.global.util.StringDateConverter;
import projectbuildup.saver.global.util.StringDateConverterWithPoint;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateChallengeRequestDto {

    private String startDate;
    private String endDate;
    private String mainTitle;
    private String subTitle;
    private String content;
    private Long savingAmount;

    public Challenge toEntity(){
        StringDateConverter stringDateConverter = new StringDateConverterWithPoint();

        return Challenge.builder()
                .startDate(stringDateConverter.convertToLocalDate(startDate))
                .endDate(stringDateConverter.convertToLocalDate(endDate))
                .mainTitle(this.mainTitle)
                .subTitle(this.subTitle)
                .content(this.content)
                .savingAmount(this.savingAmount)

                .build();
    }

}
