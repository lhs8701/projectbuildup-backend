package projectbuildup.saver.domain.phoneauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectbuildup.saver.domain.phoneauth.entity.MobileAuthentication;

import java.util.Optional;

@Repository
public interface MobileAuthenticationJpaRepository extends JpaRepository<MobileAuthentication, Long> {
    Optional<MobileAuthentication> findByPhoneNumber(String phoneNumber);
}
