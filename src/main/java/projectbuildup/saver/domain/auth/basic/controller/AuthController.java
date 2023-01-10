package projectbuildup.saver.domain.auth.basic.controller;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import projectbuildup.saver.domain.auth.basic.dto.LoginRequestDto;
import projectbuildup.saver.domain.auth.basic.dto.SignupRequestDto;
import projectbuildup.saver.domain.auth.basic.service.AuthService;
import projectbuildup.saver.domain.auth.jwt.dto.TokenRequestDto;
import projectbuildup.saver.domain.user.entity.User;

import javax.validation.Valid;

@Slf4j
@Tag(name = "[Member]", description = "회원과 관련된 API입니다.")
@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "회원가입", description = "회원가입")
    @PreAuthorize("permitAll()")
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto signupRequestDto){
        authService.signup(signupRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto){
        return new ResponseEntity<>(authService.login(loginRequestDto), HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/logout", headers = "X-AUTH-TOKEN")
    public ResponseEntity<?> logout(@RequestHeader("X-AUTH-TOKEN") String accessToken, @AuthenticationPrincipal User user) {
        authService.logout(accessToken, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/withdrawal", headers = "X-AUTH-TOKEN")
    public ResponseEntity<?> withdrawal(@RequestHeader("X-AUTH-TOKEN") String accessToken, @AuthenticationPrincipal User user) {
        authService.withdrawal(accessToken, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @PostMapping(value = "/reissue")
    public ResponseEntity<?> reissue(@RequestBody @Valid TokenRequestDto tokenRequestDto) {
        return new ResponseEntity<>(authService.reissue(tokenRequestDto), HttpStatus.OK);
    }
}
