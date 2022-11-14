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
import projectbuildup.saver.domain.phoneauth.service.PhoneService;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class PhoneController {

    PhoneService phoneService;

    @PostMapping("/auth/number")
    public ResponseEntity<PhoneAuthResponseDto> getNumber(@RequestBody PhoneAuthDto phoneAuthDto) {
        log.info(phoneAuthDto.toString());
        return new ResponseEntity<>(phoneService.getNumber(phoneAuthDto.getPhoneNumber()), HttpStatus.OK);
    }

    @PostMapping("/auth/verify")
    public ResponseEntity<PhoneAuthResponseDto> verifyNumber(@RequestBody PhoneAuthDto phoneAuthDto) {
        log.info(phoneAuthDto.toString());
        return new ResponseEntity<>(phoneService.verifyNumber(phoneAuthDto.getPhoneNumber(), phoneAuthDto.getCode()), HttpStatus.OK);
    }
}