package projectbuildup.saver.global.security.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import projectbuildup.saver.domain.user.entity.Member;
import projectbuildup.saver.global.error.ErrorCode;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("${spring.jwt.secret}")
    private String secretKey;

    private final static long ACCESS_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 365;
    private final static long REFRESH_TOKEN_EXPIRATION = 2000L * 60 * 60 * 24 * 365;
    private final UserDetailsService userDetailsService;

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public TokenForm generateToken(Member member) {

        String accessToken = createToken(member, ACCESS_TOKEN_EXPIRATION);
        String refreshToken = createToken(member, REFRESH_TOKEN_EXPIRATION);

        return TokenForm.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String createToken(Member member, Long expireTime) {
        String authorities = member.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        String idToken = member.getIdToken();
        long now = (new Date()).getTime();
        Claims claims = Jwts.claims();
        claims.setSubject(idToken);
        claims.put("idToken", idToken);
        claims.put("authorities", authorities);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(now + expireTime))
                .signWith(getSigningKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        if (claims.get("authorities") == null) {
            throw new AccessDeniedException("권한이 없습니다.");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(secretKey))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException e) {
            log.error("잘못된 시그니처");
            throw new JwtException(String.valueOf(ErrorCode.JWT_INVALID.getCode()));
        } catch (MalformedJwtException e) {
            log.error("유효하지 않은 JWT 토큰");
            throw new JwtException(String.valueOf(ErrorCode.JWT_INVALID.getCode()));
        } catch (ExpiredJwtException e) {
            log.error("Jwt 만료");
            throw new JwtException(String.valueOf(ErrorCode.JWT_EXPIRED.getCode()));
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 토큰 형식");
        } catch (IllegalArgumentException e) {
            log.error("JWT token compact of handler are invalid.");
            throw new JwtException(String.valueOf(ErrorCode.JWT_INVALID.getCode()));
        }
        return null;
    }

    public boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(getSigningKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
}