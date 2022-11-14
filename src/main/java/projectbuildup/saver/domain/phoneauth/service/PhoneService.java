package projectbuildup.saver.domain.phoneauth.service;

import projectbuildup.saver.domain.dto.res.PhoneAuthResponseDto;

public interface PhoneService {

    public String makeSignature(String url, String timestamp, String method, String accessKey, String secretKey);
    public void sendSMS(String to, String code);
    public String getRandomCode();
    public PhoneAuthResponseDto getNumber(String phoneNumber);
    public PhoneAuthResponseDto verifyNumber(String phoneNumber, String code);
}
