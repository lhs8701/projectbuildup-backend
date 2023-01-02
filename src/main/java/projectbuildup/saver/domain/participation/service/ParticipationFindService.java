package projectbuildup.saver.domain.participation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.challenge.entity.Challenge;
import projectbuildup.saver.domain.participation.entity.Participation;
import projectbuildup.saver.domain.participation.repository.ParticipationJpaRepository;
import projectbuildup.saver.domain.user.entity.User;
import projectbuildup.saver.global.error.exception.CParticipationNotFoundException;

@Service
@RequiredArgsConstructor
public class ParticipationFindService {

    private final ParticipationJpaRepository participationJpaRepository;

    public Participation findByChallengeAndUser(Challenge challenge, User user) {
        return participationJpaRepository.findByChallengeAndUser(challenge, user).orElseThrow(CParticipationNotFoundException::new);
    }

}
