package projectbuildup.saver.domain.auth.jwt.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;


@Getter
@NoArgsConstructor
@RedisHash("logoutAccessToken")
public class LogoutAccessToken {

    @Id
    private String accessToken;

    private Long userId;

    @TimeToLive
    private Long expiration;

    public LogoutAccessToken(String accessToken, Long userId, Long remainingMilliSeconds) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.expiration = remainingMilliSeconds / 1000;
    }
}