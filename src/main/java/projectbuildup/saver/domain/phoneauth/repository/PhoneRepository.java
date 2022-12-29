package projectbuildup.saver.domain.phoneauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectbuildup.saver.domain.phoneauth.entity.Phone;

import java.util.Optional;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Long> {
    Optional<Phone> findByPhoneNumber(String phoneNumber);
}
