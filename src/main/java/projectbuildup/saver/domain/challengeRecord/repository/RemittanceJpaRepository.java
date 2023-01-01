package projectbuildup.saver.domain.challengeRecord.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.saver.domain.challengeRecord.entity.Remittance;

import java.util.List;

public interface RemittanceJpaRepository extends JpaRepository<Remittance, Long> {

    List<Remittance> findByChallengeIdAndUserId(Long challengeId, Long userId);
}
