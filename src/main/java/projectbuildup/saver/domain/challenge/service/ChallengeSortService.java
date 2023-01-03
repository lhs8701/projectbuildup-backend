package projectbuildup.saver.domain.challenge.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.challenge.entity.Challenge;

import java.time.LocalDate;
import java.util.List;

@Service
public class ChallengeSortService {

    public void sortByParticipantNumber(Boolean ascending, List<Challenge> challenges) {
        if (ascending) {
            challenges.sort((a, b) -> {
                return a.getParticipationList().size() - b.getParticipationList().size();
            });
            return;
        }
        challenges.sort((a, b) -> {
            return b.getParticipationList().size() - a.getParticipationList().size();
        });
    }

    public void sortByTotalAmount(Boolean ascending, List<Challenge> challenges){
        if (ascending) {
            challenges.sort((a, b) -> {
                return (int) (a.getSavingAmount() - b.getSavingAmount());
            });
            return;
        }
        challenges.sort((a, b) -> {
            return (int) (b.getSavingAmount() - a.getSavingAmount());
        });
    }

    public void sortByEndDate(Boolean ascending, List<Challenge> challenges){
        if (ascending) {
            challenges.sort((a, b) -> {
                return a.getEndDate().compareTo(b.getEndDate());
            });
            return;
        }
        challenges.sort((a, b) -> {
            return b.getEndDate().compareTo(a.getEndDate());
        });
    }
}
