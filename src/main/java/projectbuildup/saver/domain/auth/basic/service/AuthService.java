package projectbuildup.saver.domain.auth.basic.service;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectbuildup.saver.domain.auth.basic.dto.LoginRequestDto;
import projectbuildup.saver.domain.auth.basic.dto.SignupRequestDto;
import projectbuildup.saver.domain.auth.jwt.dto.TokenRequestDto;
import projectbuildup.saver.domain.auth.jwt.dto.TokenResponseDto;
import projectbuildup.saver.domain.auth.jwt.repository.LogoutAccessTokenRedisRepository;
import projectbuildup.saver.domain.auth.jwt.repository.RefreshTokenRedisRepository;
import projectbuildup.saver.domain.user.entity.Member;
import projectbuildup.saver.domain.auth.basic.error.exception.CWrongPasswordException;
import projectbuildup.saver.domain.user.service.UserFindService;
import projectbuildup.saver.domain.user.service.MemberService;
import projectbuildup.saver.global.security.jwt.JwtProvider;
import projectbuildup.saver.global.security.jwt.TokenForm;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserFindService userFindService;
    private final MemberService memberService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRedisRepository refreshTokenRedisRepository;
    private final LogoutAccessTokenRedisRepository logoutAccessTokenRedisRepository;

    public Long signup(SignupRequestDto signupRequestDto) {
        userFindService.validateUserExistence(signupRequestDto.getIdToken());
        return memberService.createUserBySignUp(signupRequestDto);
    }

    public TokenForm login(LoginRequestDto loginRequestDto){
        Member member = userFindService.findByIdToken(loginRequestDto.getIdToken());

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword()))
            throw new CWrongPasswordException();

        return jwtProvider.generateToken(member);
    }

    public void logout(String accessToken, Member member) {

    }

    public void withdrawal(String accessToken, Member member) {
        logout(accessToken, member);
        memberService.deleteById(member.getId());
    }

    public TokenResponseDto reissue(TokenRequestDto tokenRequestDto) {
        return null;
    }
}
