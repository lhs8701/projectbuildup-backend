package projectbuildup.saver.domain.dto.res;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetChallengeListResDto {
    private Long challengCnt;
    private List<GetChallengeResponseDto> challengeList;
}
