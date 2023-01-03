package projectbuildup.saver.domain.dto.res;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetChallengeListResDto {
    private Long numOfElement;
    private List<ChallengeResponseDto> challengeList;

    public GetChallengeListResDto(List<ChallengeResponseDto> list){
        this.numOfElement = (long) list.size();
        challengeList = list;
    }
}
