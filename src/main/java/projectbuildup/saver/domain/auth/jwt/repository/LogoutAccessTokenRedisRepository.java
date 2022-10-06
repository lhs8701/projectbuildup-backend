package projectbuildup.saver.domain.auth.jwt.repository;

import org.springframework.data.repository.CrudRepository;
import projectbuildup.saver.domain.auth.jwt.entity.LogoutAccessToken;

public interface LogoutAccessTokenRedisRepository extends CrudRepository<LogoutAccessToken, String> {
}
