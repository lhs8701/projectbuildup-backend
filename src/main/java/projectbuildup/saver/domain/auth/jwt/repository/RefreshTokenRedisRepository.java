package projectbuildup.saver.domain.auth.jwt.repository;

import org.springframework.data.repository.CrudRepository;
import projectbuildup.saver.domain.auth.jwt.entity.RefreshToken;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, Long> {
}
