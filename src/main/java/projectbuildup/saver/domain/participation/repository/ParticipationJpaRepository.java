package projectbuildup.saver.domain.participation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.saver.domain.challenge.entity.Challenge;
import projectbuildup.saver.domain.participation.entity.Participation;
import projectbuildup.saver.domain.user.entity.Member;

import java.util.List;
import java.util.Optional;

public interface ParticipationJpaRepository extends JpaRepository<Participation, Long> {
    List<Participation> findByChallenge_Id(Long challengeId);
    Optional<Participation> findByChallengeAndMember(Challenge challenge, Member member);
    void deleteByChallengeAndMember(Challenge challenge, Member member);
    List<Participation> findAllByChallenge(Challenge challenge);
    List<Participation> findAllByMember(Member member);
}
