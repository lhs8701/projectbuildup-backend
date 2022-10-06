package projectbuildup.saver.domain.auth.jwt.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Getter
@NoArgsConstructor
@RedisHash("activeAccessToken")
public class ActiveAccessToken {
    @Id
    private String accessToken;

    private Long userId;

    @TimeToLive
    private Long expiration;

    public ActiveAccessToken(String accessToken, Long userId) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.expiration = JwtExpiration.ACCESS_TOKEN_EXPIRATION_TIME.getValue() / 1000;
    }
}
