package projectbuildup.saver.domain.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ViewChallengeResDto {
    private String mainTitle;
    private String subTitle;
    private Long savingAmount;
    private Long participants;
    private LocalDateTime endDate;
    private String content;
}
