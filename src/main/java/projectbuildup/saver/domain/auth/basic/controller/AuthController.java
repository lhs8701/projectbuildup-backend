package projectbuildup.saver.domain.auth.basic.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import projectbuildup.saver.domain.auth.basic.dto.LoginRequestDto;
import projectbuildup.saver.domain.auth.basic.dto.SignupRequestDto;
import projectbuildup.saver.domain.auth.basic.service.AuthService;
import projectbuildup.saver.domain.auth.jwt.dto.TokenRequestDto;
import projectbuildup.saver.domain.auth.jwt.dto.TokenResponseDto;
import projectbuildup.saver.domain.user.entity.UserEntity;

import javax.validation.Valid;

@Slf4j
@Api(tags = {"Basic Auth"})
@RequiredArgsConstructor
@RequestMapping("/open/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @ApiOperation(value="회원가입")
    @PreAuthorize("permitAll()")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto signupRequestDto){
        authService.signup(signupRequestDto);
        return ResponseEntity.ok("회원가입 성공");
    }

    @ApiOperation(value="로그인")
    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "AccessToken",
                    required = true, dataType = "String", paramType = "header"
            )
    })
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/logout", headers = "X-AUTH-TOKEN")
    public ResponseEntity<?> logout(@RequestHeader("X-AUTH-TOKEN") String accessToken, @AuthenticationPrincipal UserEntity user) {
        authService.logout(accessToken, user);
        return ResponseEntity.ok("로그아웃 성공");
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "AccessToken",
                    required = true, dataType = "String", paramType = "header"
            )
    })
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/withdrawal", headers = "X-AUTH-TOKEN")
    public ResponseEntity<?> withdrawal(@RequestHeader("X-AUTH-TOKEN") String accessToken, @AuthenticationPrincipal UserEntity user) {
        authService.withdrawal(accessToken, user);
        return ResponseEntity.ok("회원 탈퇴 성공");
    }

    @ApiOperation(value = "액세스, 리프레시 토큰 재발급")
    @PreAuthorize("permitAll()")
    @PostMapping(value = "/reissue")
    public ResponseEntity<?> reissue(@RequestBody @Valid TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
}
