package projectbuildup.saver.domain.remittance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.saver.domain.challenge.entity.Challenge;
import projectbuildup.saver.domain.remittance.entity.Remittance;
import projectbuildup.saver.domain.user.entity.Member;

import java.util.List;

public interface RemittanceJpaRepository extends JpaRepository<Remittance, Long> {

    List<Remittance> findByChallengeIdAndUserId(Long challengeId, Long userId);
    List<Remittance> findAllByChallengeAndUser(Challenge challenge, Member member);
}
