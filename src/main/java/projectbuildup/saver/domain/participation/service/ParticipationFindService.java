package projectbuildup.saver.domain.participation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.challenge.entity.Challenge;
import projectbuildup.saver.domain.participation.entity.Participation;
import projectbuildup.saver.domain.participation.repository.ParticipationJpaRepository;
import projectbuildup.saver.domain.user.entity.Member;
import projectbuildup.saver.global.error.exception.CParticipationNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipationFindService {

    private final ParticipationJpaRepository participationJpaRepository;

    public Participation findByChallengeAndUser(Challenge challenge, Member member) {
        return participationJpaRepository.findByChallengeAndUser(challenge, member).orElseThrow(CParticipationNotFoundException::new);
    }

    public List<Participation> findAllByChallenge(Challenge challenge) {
        return participationJpaRepository.findAllByChallenge(challenge);
    }
    public List<Participation> findAllByUser(Member member){
        return participationJpaRepository.findAllByUser(member);
    }
}
