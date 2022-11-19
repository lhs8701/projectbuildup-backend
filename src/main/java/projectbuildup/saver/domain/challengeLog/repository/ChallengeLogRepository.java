package projectbuildup.saver.domain.challengeLog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.saver.domain.challenge.entity.ChallengeEntity;
import projectbuildup.saver.domain.challengeLog.entity.ChallengeLogEntity;
import projectbuildup.saver.domain.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface ChallengeLogRepository extends JpaRepository<ChallengeLogEntity, Long> {
    List<ChallengeLogEntity> findByChallenge_Id(Long challengeId);
    Optional<ChallengeLogEntity> findByChallengeAndUser(ChallengeEntity challengeId, UserEntity userId);
}
