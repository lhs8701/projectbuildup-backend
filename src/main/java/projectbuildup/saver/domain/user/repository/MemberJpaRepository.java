package projectbuildup.saver.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.saver.domain.user.entity.Member;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByIdToken(String idToken);
}
