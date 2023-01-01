package projectbuildup.saver.domain.challenge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.saver.domain.challenge.entity.Challenge;

public interface ChallengeJpaRepository extends JpaRepository<Challenge, Long> {

}
