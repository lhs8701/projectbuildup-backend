package projectbuildup.saver.domain.saving.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.saver.domain.saving.entity.SavingEntity;

import java.util.List;

public interface SavingRepository extends JpaRepository<SavingEntity, Long> {

    List<SavingEntity> findByChallengeIdAndUserId(Long challengeId, Long userId);
}
