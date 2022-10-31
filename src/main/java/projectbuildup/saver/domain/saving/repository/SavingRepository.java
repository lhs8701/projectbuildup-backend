package projectbuildup.saver.domain.saving.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.saver.domain.saving.entity.SavingEntity;

public interface SavingRepository extends JpaRepository<SavingEntity, Long> {
}
