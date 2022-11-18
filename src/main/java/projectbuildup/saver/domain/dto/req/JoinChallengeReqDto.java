package projectbuildup.saver.domain.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JoinChallengeReqDto {
    private String loginId;
    private Long challengeId;
}
