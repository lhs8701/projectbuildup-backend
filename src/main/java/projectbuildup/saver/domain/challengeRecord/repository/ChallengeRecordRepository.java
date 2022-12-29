package projectbuildup.saver.domain.challengeRecord.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.saver.domain.challengeRecord.entity.ChallengeRecordEntity;

import java.util.List;

public interface ChallengeRecordRepository extends JpaRepository<ChallengeRecordEntity, Long> {

    List<ChallengeRecordEntity> findByChallengeIdAndUserId(Long challengeId, Long userId);
}
