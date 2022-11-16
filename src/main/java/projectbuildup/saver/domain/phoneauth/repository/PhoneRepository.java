package projectbuildup.saver.domain.phoneauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectbuildup.saver.domain.phoneauth.entity.PhoneEntity;

import java.util.Optional;

@Repository
public interface PhoneRepository extends JpaRepository<PhoneEntity, Long> {
    Optional<PhoneEntity> findByPhoneNumber(String phoneNumber);
}
