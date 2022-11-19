package projectbuildup.saver.domain.dto.req;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JoinChallengeReqDto {
    private String loginId;
    private Long challengeId;
}
