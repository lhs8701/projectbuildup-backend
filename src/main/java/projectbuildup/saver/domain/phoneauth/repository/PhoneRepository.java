package projectbuildup.saver.domain.phoneauth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import projectbuildup.saver.domain.phoneauth.entity.PhoneEntity;

import java.util.Optional;

@Repository
public interface PhoneRepository extends CrudRepository<PhoneEntity, Long> {
    public Optional<PhoneEntity> findByPhoneNumber(String phoneNumber);
}
