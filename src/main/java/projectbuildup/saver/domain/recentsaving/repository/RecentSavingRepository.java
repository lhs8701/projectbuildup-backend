package projectbuildup.saver.domain.recentsaving.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.saver.domain.recentsaving.entity.RecentSaving;
import projectbuildup.saver.domain.user.entity.UserEntity;

import java.util.Optional;

public interface RecentSavingRepository extends JpaRepository<RecentSaving, Long> {
    Optional<RecentSaving> findByUserEntity(UserEntity user);
}
