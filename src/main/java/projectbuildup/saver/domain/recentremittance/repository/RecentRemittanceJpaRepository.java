package projectbuildup.saver.domain.recentremittance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projectbuildup.saver.domain.recentremittance.entity.RecentRemittance;
import projectbuildup.saver.domain.user.entity.Member;

import java.util.Optional;

public interface RecentRemittanceJpaRepository extends JpaRepository<RecentRemittance, Long> {
    Optional<RecentRemittance> findByUser(Member member);
}
