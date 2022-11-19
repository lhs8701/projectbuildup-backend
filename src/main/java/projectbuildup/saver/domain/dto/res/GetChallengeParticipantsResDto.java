package projectbuildup.saver.domain.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetChallengeParticipantsResDto {
    private List<ParticipantResDto> participantResDtoList;
    private Long participantCnt;
}
