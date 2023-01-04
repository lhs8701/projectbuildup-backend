package projectbuildup.saver.domain.participation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.challenge.entity.Challenge;
import projectbuildup.saver.domain.challenge.error.exception.CUserAlreadyJoinedException;
import projectbuildup.saver.domain.participation.entity.Participation;
import projectbuildup.saver.domain.participation.repository.ParticipationJpaRepository;
import projectbuildup.saver.domain.user.entity.User;

@Service
@RequiredArgsConstructor
public class ParticipationService {

    private final ParticipationJpaRepository participationJpaRepository;
    public void validateParticipationExistence(Challenge challenge, User user){
        if (participationJpaRepository.findByChallengeAndUser(challenge, user).isPresent()){
            throw new CUserAlreadyJoinedException();
        }
    }

    public void makeParticipation(Challenge challenge, User user) {
        Participation participation = Participation.builder()
                .user(user)
                .challenge(challenge)
                .build();

        participationJpaRepository.save(participation);
    }

    public void deleteByChallengeAndUser(Challenge challenge, User user) {
        participationJpaRepository.deleteByChallengeAndUser(challenge, user);
    }
}
