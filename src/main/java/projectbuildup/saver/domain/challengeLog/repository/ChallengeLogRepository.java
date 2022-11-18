package projectbuildup.saver.domain.challengeLog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.saver.domain.challengeLog.entity.ChallengeLogEntity;

import java.util.List;

public interface ChallengeLogRepository extends JpaRepository<ChallengeLogEntity, Long> {
    List<ChallengeLogEntity> findByChallenge_Id(Long challengeId);
}
