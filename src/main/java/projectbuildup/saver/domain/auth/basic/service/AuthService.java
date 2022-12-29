package projectbuildup.saver.domain.auth.basic.service;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectbuildup.saver.domain.auth.basic.dto.LoginRequestDto;
import projectbuildup.saver.domain.auth.basic.dto.SignupRequestDto;
import projectbuildup.saver.domain.auth.basic.error.exception.CRefreshTokenExpiredException;
import projectbuildup.saver.domain.auth.basic.error.exception.CWrongRefreshTokenException;
import projectbuildup.saver.domain.auth.jwt.dto.TokenRequestDto;
import projectbuildup.saver.domain.auth.jwt.dto.TokenResponseDto;
import projectbuildup.saver.domain.auth.jwt.entity.LogoutAccessToken;
import projectbuildup.saver.domain.auth.jwt.entity.RefreshToken;
import projectbuildup.saver.domain.auth.jwt.repository.LogoutAccessTokenRedisRepository;
import projectbuildup.saver.domain.auth.jwt.repository.RefreshTokenRedisRepository;
import projectbuildup.saver.domain.user.entity.User;
import projectbuildup.saver.domain.user.repository.UserJpaRepository;
import projectbuildup.saver.domain.user.error.exception.CUserExistException;
import projectbuildup.saver.domain.user.error.exception.CUserNotFoundException;
import projectbuildup.saver.domain.auth.basic.error.exception.CWrongPasswordException;
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
        if (userJpaRepository.findByIdToken(signupRequestDto.getIdToken()).isPresent())
            throw new CUserExistException();
        return userJpaRepository.save(signupRequestDto.toEntity(passwordEncoder)).getId();
    }

    public TokenResponseDto login(LoginRequestDto loginRequestDto){
        User user = userJpaRepository.findByIdToken(loginRequestDto.getIdToken()).orElseThrow(CUserNotFoundException::new);

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword()))
            throw new CWrongPasswordException();

        String accessToken = jwtProvider.generateAccessToken(user.getIdToken(), user.getRoles());
        String refreshToken = jwtProvider.generateRefreshToken(user.getIdToken(),user.getRoles());

        refreshTokenRedisRepository.save(new RefreshToken(user.getId(), refreshToken));

        return TokenResponseDto.builder()
                .grantType("bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void logout(String accessToken, User user) {
        long remainMilliSeconds = jwtProvider.getExpiration(accessToken);

        refreshTokenRedisRepository.deleteById(user.getId());
        logoutAccessTokenRedisRepository.save(new LogoutAccessToken(accessToken, user.getId(), remainMilliSeconds));
    }

    public void withdrawal(String accessToken, User user) {
        logout(accessToken, user);
        userJpaRepository.deleteById(user.getId());
    }

    public TokenResponseDto reissue(TokenRequestDto tokenRequestDto) {

        String existAccessToken = tokenRequestDto.getAccessToken();
        Authentication authentication = jwtProvider.getAuthentication(existAccessToken);
        User user = (User)authentication.getPrincipal();

        String existRefreshToken = tokenRequestDto.getRefreshToken();
        RefreshToken existRedisRefreshToken = refreshTokenRedisRepository.findById(user.getId()).orElseThrow(CRefreshTokenExpiredException::new);

        if (existRefreshToken.equals(existRedisRefreshToken.getRefreshToken())) {
            String newAccessToken = jwtProvider.generateAccessToken(user.getIdToken(), user.getRoles());
            String newRefreshToken = jwtProvider.generateRefreshToken(user.getIdToken(), user.getRoles());
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
