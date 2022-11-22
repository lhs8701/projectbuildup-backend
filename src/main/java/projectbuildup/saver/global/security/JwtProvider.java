package projectbuildup.saver.global.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import projectbuildup.saver.domain.auth.jwt.entity.JwtExpiration;
import projectbuildup.saver.domain.auth.jwt.repository.LogoutAccessTokenRedisRepository;
import projectbuildup.saver.global.error.ErrorCode;
import projectbuildup.saver.global.error.exception.CAuthenticationEntryPointException;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("${spring.jwt.secret}") //암호키는 중요하므로 따로 빼서 관리
    private String secretKey;
    private final UserDetailsService userDetailsService;
    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;
    private String ROLES = "roles";


    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(String userAccount, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(userAccount);
        claims.put("roles", roles);
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + JwtExpiration.ACCESS_TOKEN_EXPIRATION_TIME.getValue()))
                .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken(String userAccount, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(String.valueOf(userAccount));
        claims.put("roles", roles);
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setExpiration(new Date(now.getTime() + JwtExpiration.REFRESH_TOKEN_EXPIRATION_TIME.getValue()))
                .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }


    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        if (claims.get(ROLES) == null) { //권한 있는지 확인
            throw new CAuthenticationEntryPointException();
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject()); //pk값을 가지고 loadUserByUsername()을 통해 유저 엔티티를 받는다.
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 만료된 토큰이여도 refresh token을 검증 후 재발급할 수 있도록 claims 반환
    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSigningKey(secretKey)).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public long getExpiration(String token) {
        Date expiration = Jwts.parserBuilder().setSigningKey(getSigningKey(secretKey)).build().parseClaimsJws(token).getBody().getExpiration();
        Date now = new Date();
        return expiration.getTime() - now.getTime();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    public boolean validateToken(HttpServletRequest request, String jwt) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey(secretKey)).build().parseClaimsJws(jwt);
            if (logoutAccessTokenRedisRepository.existsById(jwt)) {
                throw new JwtException(String.valueOf(ErrorCode.EXPIRED_TOKEN_EXCEPTION.getCode()));
            }
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            throw new JwtException(String.valueOf(ErrorCode.WRONG_TYPE_TOKEN_EXCEPTION.getCode()));
        } catch (ExpiredJwtException e) {
            throw new JwtException(String.valueOf(ErrorCode.EXPIRED_TOKEN_EXCEPTION.getCode()));
        } catch (UnsupportedJwtException e) {
            throw new JwtException(String.valueOf(ErrorCode.UNSUPPORTED_TOKEN_EXCEPTION.getCode()));
        } catch (IllegalArgumentException e) {
            throw new JwtException(String.valueOf(ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getCode()));
        }
    }
}