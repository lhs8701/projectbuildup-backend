package projectbuildup.saver.domain.auth.jwt.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtExpiration {

    ACCESS_TOKEN_EXPIRATION_TIME("JWT 만료 시간 / 60분", 1000L * 60 * 60),
    REFRESH_TOKEN_EXPIRATION_TIME("Refresh 토큰 만료 시간 / 14일", 1000L * 60 * 60 * 24 * 14);

//    ACCESS_TOKEN_EXPIRATION_TIME("JWT 만료 시간 / 30초", 1000L * 30),
//    REFRESH_TOKEN_EXPIRATION_TIME("Refresh 토큰 만료 시간 / 60초", 1000L * 60);

    private final String description;
    private final Long value;
}