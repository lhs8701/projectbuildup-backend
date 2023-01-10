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
import projectbuildup.saver.domain.user.entity.Member;
import projectbuildup.saver.domain.auth.basic.error.exception.CWrongPasswordException;
import projectbuildup.saver.domain.user.service.UserFindService;
import projectbuildup.saver.domain.user.service.UserService;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserFindService userFindService;
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;

    public Long signup(SignupRequestDto signupRequestDto) {
        userFindService.validateUserExistence(signupRequestDto.getIdToken());
        return userService.createUserBySignUp(signupRequestDto);
    }

    public TokenResponseDto login(LoginRequestDto loginRequestDto){
        Member member = userFindService.findByIdToken(loginRequestDto.getIdToken());

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword()))
            throw new CWrongPasswordException();

        String accessToken = jwtProvider.generateAccessToken(member.getIdToken(), member.getRoles());
        String refreshToken = jwtProvider.generateRefreshToken(member.getIdToken(), member.getRoles());

        refreshTokenRedisRepository.save(new RefreshToken(member.getId(), refreshToken));

        return TokenResponseDto.builder()
                .grantType("bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void logout(String accessToken, Member member) {
        long remainMilliSeconds = jwtProvider.getExpiration(accessToken);

        refreshTokenRedisRepository.deleteById(member.getId());
        logoutAccessTokenRedisRepository.save(new LogoutAccessToken(accessToken, member.getId(), remainMilliSeconds));
    }

    public void withdrawal(String accessToken, Member member) {
        logout(accessToken, member);
        userService.deleteById(member.getId());
    }

    public TokenResponseDto reissue(TokenRequestDto tokenRequestDto) {

        String existAccessToken = tokenRequestDto.getAccessToken();
        Authentication authentication = jwtProvider.getAuthentication(existAccessToken);
        Member member = (Member)authentication.getPrincipal();

        String existRefreshToken = tokenRequestDto.getRefreshToken();
        RefreshToken existRedisRefreshToken = refreshTokenRedisRepository.findById(member.getId()).orElseThrow(CRefreshTokenExpiredException::new);

        if (existRefreshToken.equals(existRedisRefreshToken.getRefreshToken())) {
            String newAccessToken = jwtProvider.generateAccessToken(member.getIdToken(), member.getRoles());
            String newRefreshToken = jwtProvider.generateRefreshToken(member.getIdToken(), member.getRoles());
            refreshTokenRedisRepository.save(new RefreshToken(member.getId(), newRefreshToken));
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
