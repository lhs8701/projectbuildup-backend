package projectbuildup.saver.domain.phoneauth.service;

import projectbuildup.saver.domain.dto.res.PhoneAuthResponseDto;

public interface PhoneService {
    public boolean send_message(String content, String phone);
    public String getRandomCode();
    public PhoneAuthResponseDto getNumber(String phoneNumber);
    public PhoneAuthResponseDto verifyNumber(String phoneNumber, String code);
}
