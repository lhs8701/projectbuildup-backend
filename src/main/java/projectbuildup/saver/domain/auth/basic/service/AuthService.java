package projectbuildup.saver.domain.auth.basic.service;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectbuildup.saver.domain.auth.basic.dto.LoginRequestDto;
import projectbuildup.saver.domain.auth.basic.dto.SignupRequestDto;
import projectbuildup.saver.domain.auth.jwt.dto.TokenRequestDto;
import projectbuildup.saver.domain.auth.jwt.dto.TokenResponseDto;
import projectbuildup.saver.domain.auth.jwt.entity.LogoutAccessToken;
import projectbuildup.saver.domain.auth.jwt.entity.RefreshToken;
import projectbuildup.saver.domain.auth.jwt.repository.LogoutAccessTokenRedisRepository;
import projectbuildup.saver.domain.auth.jwt.repository.RefreshTokenRedisRepository;
import projectbuildup.saver.domain.user.entity.UserEntity;
import projectbuildup.saver.domain.user.repository.UserJpaRepository;
import projectbuildup.saver.global.error.exception.*;
import projectbuildup.saver.global.security.JwtProvider;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserJpaRepository userJpaRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;

    public Long signup(SignupRequestDto signupRequestDto) {
        if (userJpaRepository.findByLoginId(signupRequestDto.getLoginId()).isPresent())
            throw new CUserExistException();
        log.info(signupRequestDto.getPassword());
        return userJpaRepository.save(signupRequestDto.toEntity(passwordEncoder)).getId();
    }

    public TokenResponseDto login(LoginRequestDto loginRequestDto){
        UserEntity user = userJpaRepository.findByLoginId(loginRequestDto.getLoginId()).orElseThrow(CUserNotFoundException::new);

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword()))
            throw new CWrongPasswordException();

        String accessToken = jwtProvider.generateAccessToken(user.getLoginId(), user.getRoles());
        String refreshToken = jwtProvider.generateRefreshToken(user.getLoginId(),user.getRoles());

        refreshTokenRedisRepository.save(new RefreshToken(user.getId(), refreshToken));

        return TokenResponseDto.builder()
                .grantType("bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void logout(String accessToken) {
        Authentication authentication = jwtProvider.getAuthentication(accessToken);
        UserEntity user = (UserEntity) authentication.getPrincipal();
        long remainMilliSeconds = jwtProvider.getExpiration(accessToken);

        refreshTokenRedisRepository.deleteById(user.getId());
        logoutAccessTokenRedisRepository.save(new LogoutAccessToken(accessToken, user.getId(), remainMilliSeconds));
    }

    public void withdrawal(String accessToken, UserEntity user) {
        long remainMilliSeconds = jwtProvider.getExpiration(accessToken);

        refreshTokenRedisRepository.deleteById(user.getId());
        logoutAccessTokenRedisRepository.save(new LogoutAccessToken(accessToken, user.getId(), remainMilliSeconds));
        userJpaRepository.deleteById(user.getId());
    }

    public TokenResponseDto reissue(TokenRequestDto tokenRequestDto) {

        String existAccessToken = tokenRequestDto.getAccessToken();
        Authentication authentication = jwtProvider.getAuthentication(existAccessToken);
        UserEntity user = (UserEntity)authentication.getPrincipal();

        String existRefreshToken = tokenRequestDto.getRefreshToken();
        RefreshToken existRedisRefreshToken = refreshTokenRedisRepository.findById(user.getId()).orElseThrow(CRefreshTokenExpiredException::new);

        if (existRefreshToken.equals(existRedisRefreshToken.getRefreshToken())) {
            String newAccessToken = jwtProvider.generateAccessToken(user.getLoginId(), user.getRoles());
            String newRefreshToken = jwtProvider.generateRefreshToken(user.getLoginId(), user.getRoles());
            refreshTokenRedisRepository.save(new RefreshToken(user.getId(), newRefreshToken));
            return TokenResponseDto.builder()
                    .grantType("bearer")
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .build();
        }
        else {
            throw new CWrongRefreshTokenException();
        }
    }
}
