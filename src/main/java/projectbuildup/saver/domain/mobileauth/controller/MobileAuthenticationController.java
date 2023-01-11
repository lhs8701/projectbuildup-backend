package projectbuildup.saver.domain.mobileauth.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import projectbuildup.saver.domain.dto.req.AuthenticationCodeCheckRequestDto;
import projectbuildup.saver.domain.dto.req.AuthenticationCodeRequestDto;
import projectbuildup.saver.domain.dto.res.PhoneAuthResponseDto;
import projectbuildup.saver.domain.mobileauth.service.MobileAuthenticationService;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class MobileAuthenticationController {

    MobileAuthenticationService mobileAuthenticationService;

    // 유효한 전화번호인지 확인하는 API
    @PostMapping("/auth/number")
    public ResponseEntity<PhoneAuthResponseDto> getNumber(@RequestBody AuthenticationCodeRequestDto authenticationCodeRequestDto) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        PhoneAuthResponseDto ans = mobileAuthenticationService.getNumber(authenticationCodeRequestDto.getPhoneNumber());
        return new ResponseEntity<>(ans, HttpStatus.OK);
    }

    //인증번호 검증 ->
    @PostMapping("/auth/verify")
    public ResponseEntity<PhoneAuthResponseDto> verifyNumber(@RequestBody AuthenticationCodeCheckRequestDto authenticationCodeCheckRequestDto) {
        PhoneAuthResponseDto ans = mobileAuthenticationService.verifyNumber(authenticationCodeCheckRequestDto.getPhoneNumber(), authenticationCodeCheckRequestDto.getCode());
        return new ResponseEntity<>(ans, HttpStatus.OK);
    }
}
