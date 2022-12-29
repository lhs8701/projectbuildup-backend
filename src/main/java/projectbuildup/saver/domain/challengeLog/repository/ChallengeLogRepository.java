package projectbuildup.saver.domain.challengeLog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.saver.domain.challenge.entity.Challenge;
import projectbuildup.saver.domain.challengeLog.entity.ChallengeLog;
import projectbuildup.saver.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface ChallengeLogRepository extends JpaRepository<ChallengeLog, Long> {
    List<ChallengeLog> findByChallenge_Id(Long challengeId);
    Optional<ChallengeLog> findByChallengeAndUser(Challenge challenge, User user);

    void deleteByChallengeAndUser(Challenge challenge, User user);
}
