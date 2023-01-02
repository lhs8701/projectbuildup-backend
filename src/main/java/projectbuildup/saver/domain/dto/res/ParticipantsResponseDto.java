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
public class ParticipantsResponseDto {
    private List<ParticipantDto> responseDtoList;
    private Long joinedNumber;

    public ParticipantsResponseDto(List<ParticipantDto> list) {
        this.responseDtoList = list;
        this.joinedNumber = (long) list.size();
    }
}
