package projectbuildup.saver.domain.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetChallengesReqDto {
    private Long sortType;
    private boolean ascending;
    private String loginId;
}
