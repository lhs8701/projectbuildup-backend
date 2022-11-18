package projectbuildup.saver.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.saver.domain.user.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByLoginId(String userId);
}
