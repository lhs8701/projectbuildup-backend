package projectbuildup.saver.domain.phoneauth.service.interfaces;

import projectbuildup.saver.domain.dto.res.PhoneAuthResponseDto;

public interface PhoneService {
    public PhoneAuthResponseDto getNumber(String phoneNumber);
    public PhoneAuthResponseDto verifyNumber(String phoneNumber, String code);
}
