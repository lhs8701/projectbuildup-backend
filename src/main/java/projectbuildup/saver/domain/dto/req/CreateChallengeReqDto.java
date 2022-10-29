package projectbuildup.saver.domain.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateChallengeReqDto {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String mainTitle;
    private String subTitle;
    private String content;
    private Long savingAmount;
}
