package projectbuildup.saver.domain.phoneauth.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import projectbuildup.saver.domain.dto.req.PhoneAuthDto;
import projectbuildup.saver.domain.dto.res.PhoneAuthResponseDto;
import projectbuildup.saver.domain.phoneauth.service.interfaces.PhoneService;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class PhoneController {

    PhoneService phoneService;

    // 유효한 전화번호인지 확인하는 API
    @PostMapping("/auth/number")
    public ResponseEntity<PhoneAuthResponseDto> getNumber(@RequestBody PhoneAuthDto phoneAuthDto) {
        PhoneAuthResponseDto ans = phoneService.getNumber(phoneAuthDto.getPhoneNumber());
        return ans.getStat() ? new ResponseEntity<>(ans, HttpStatus.OK) : new ResponseEntity<>(ans, HttpStatus.NOT_ACCEPTABLE);
    }

    //인증번호 검증 ->
    @PostMapping("/auth/verify")
    public ResponseEntity<PhoneAuthResponseDto> verifyNumber(@RequestBody PhoneAuthDto phoneAuthDto) {
        PhoneAuthResponseDto ans = phoneService.verifyNumber(phoneAuthDto.getPhoneNumber(), phoneAuthDto.getCode());
        return ans.getStat() ? new ResponseEntity<>(ans, HttpStatus.OK) : new ResponseEntity<>(ans, HttpStatus.NOT_ACCEPTABLE);
    }
}
