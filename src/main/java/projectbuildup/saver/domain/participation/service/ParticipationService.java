package projectbuildup.saver.domain.participation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.challenge.entity.Challenge;
import projectbuildup.saver.domain.challenge.error.exception.CUserAlreadyJoinedException;
import projectbuildup.saver.domain.participation.entity.Participation;
import projectbuildup.saver.domain.participation.repository.ParticipationJpaRepository;
import projectbuildup.saver.domain.user.entity.Member;

@Service
@RequiredArgsConstructor
public class ParticipationService {

    private final ParticipationJpaRepository participationJpaRepository;
    public void validateParticipationExistence(Challenge challenge, Member member){
        if (participationJpaRepository.findByChallengeAndUser(challenge, member).isPresent()){
            throw new CUserAlreadyJoinedException();
        }
    }

    public void makeParticipation(Challenge challenge, Member member) {
        Participation participation = Participation.builder()
                .user(member)
                .challenge(challenge)
                .build();

        participationJpaRepository.save(participation);
    }

    public void deleteByChallengeAndUser(Challenge challenge, Member member) {
        participationJpaRepository.deleteByChallengeAndUser(challenge, member);
    }
}
