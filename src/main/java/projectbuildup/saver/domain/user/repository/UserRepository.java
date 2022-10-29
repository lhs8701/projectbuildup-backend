package projectbuildup.saver.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.saver.domain.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
