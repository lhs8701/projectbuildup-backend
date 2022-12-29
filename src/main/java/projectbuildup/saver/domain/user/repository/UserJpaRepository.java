package projectbuildup.saver.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.saver.domain.user.entity.User;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User,Long> {
    Optional<User> findByIdToken(String idToken);
}
