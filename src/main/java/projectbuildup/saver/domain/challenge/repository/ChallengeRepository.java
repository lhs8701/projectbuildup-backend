package projectbuildup.saver.domain.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.saver.domain.challenge.entity.ChallengeEntity;

public interface ChallengeRepository extends JpaRepository<ChallengeEntity, Long> {

}
