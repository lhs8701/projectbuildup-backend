package projectbuildup.saver.domain.dto.req;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JoinChallengeReqDto {
    private Long memberId;
    private Long challengeId;
}
