package projectbuildup.saver.domain.phoneauth.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.dto.res.PhoneAuthResponseDto;
import projectbuildup.saver.domain.phoneauth.entity.PhoneEntity;
import projectbuildup.saver.domain.phoneauth.repository.PhoneRepository;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.Random;

import java.util.regex.Pattern;

@Service
@AllArgsConstructor
@Slf4j
public class PhoneServiceImpl implements PhoneService{

    PhoneRepository phoneRepository;

    @Override
    public boolean send_message(String content, String phone) {
        String phoneNumber = phone;

        String errCode = "404";
        String timestamp = Long.toString(System.currentTimeMillis());

        String serviceId = "ncp:sms:kr:293656383759:saver_phone";
        String secretKey = "AtzSAHcUZNflEDYbKSClDluSIMaXOLFE4xYMUPrX";
        String accessKey = "kEIEdgWRluUtJXy1E51R";
        String my_number = "01021634980";

        String method = "POST";
        String space = " ";
        String newLine = "\n";
        String url = String.format("https://sens.apigw.ntruss.com/sms/v2/services/%s/messages", serviceId);
        String url2 = String.format("/sms/v2/services/%s/messages", serviceId);

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url2)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = null;

        try {
            signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        try {
            mac.init(signingKey);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        byte[] rawHmac = new byte[0];
        try {
            rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        String signature = Base64.encodeBase64String(rawHmac);

        // POST API 구현 해야됨

        return true;
    }

    @Override
    public String getRandomCode() {
        int leftLimit = 48;
        int rightLimit = 57;
        int targetStringLength = 6;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

    @Override
    @Transactional
    public PhoneAuthResponseDto getNumber(String phoneNumber) {
        try {

            // 값 검증
            if(!Pattern.matches("[0-9]{11}", phoneNumber)) {
                return new PhoneAuthResponseDto(false, "올바르지 않은 전화번호입니다.");
            }


            // 현재 저장되어 있는 전화번호가 있는지 확인하기 위함.
            Optional<PhoneEntity> phone = phoneRepository.findByPhoneNumber(phoneNumber);
            String code = getRandomCode();

            // 전화번호가 저장되어 있으면 코드만 변경해주고, 없을시 새로 생성해준다.
            if(phone.isPresent()) {
                PhoneEntity unwrappedPhone = phone.get();
                unwrappedPhone.setCode(code);
                unwrappedPhone.setTried(0L);
                phoneRepository.save(unwrappedPhone);
            } else {
                PhoneEntity newPhone = new PhoneEntity(phoneNumber, code, 0L);
                phoneRepository.save(newPhone);
            }

            // 나중에 여기서 메시지 전송.

            log.info(String.format("%s %s", phoneNumber, code));
        } catch (Exception e) {
            return new PhoneAuthResponseDto(false, "실패했습니다.");
        }

        return new PhoneAuthResponseDto(true, "성공했습니다.");
    }

    @Override
    public PhoneAuthResponseDto verifyNumber(String phoneNumber, String code) {
        Optional<PhoneEntity> phone = phoneRepository.findByPhoneNumber(phoneNumber);
        
        // 값 검증
        if(!Pattern.matches("[0-9]{11}", phoneNumber)) {
            return new PhoneAuthResponseDto(false, "올바르지 않은 전화번호입니다.");
        }
        if(!Pattern.matches("[0-9]{6}", code)) {
            return new PhoneAuthResponseDto(false, "올바르지 않은 코드입니다.");
        }

        // 받아온 전화번호에 인증 정보가 있는지 확인
        if(phone.isEmpty()) {
            return new PhoneAuthResponseDto(false, "인증 중이 아닙니다, 인증번호 전송 버튼을 눌러주세요");
        }

        PhoneEntity unwrappedPhone = phone.get();

        // 이미 시도를 너무 많이 했으면 거절
        if(unwrappedPhone.getTried() >= 5) {
            return new PhoneAuthResponseDto(false, "코드 입력 횟수가 초과되었습니다, 다시 인증해주세요");
        }

        log.info(String.format("%s %s", unwrappedPhone.getCode(), code));

        // 인증 확인 혹은 실패
        if(code.equals(unwrappedPhone.getCode())) {
            phoneRepository.delete(unwrappedPhone);
            return new PhoneAuthResponseDto(true, "성공했습니다.");
        } else {
            unwrappedPhone.setTried(unwrappedPhone.getTried()+1);
            phoneRepository.save(unwrappedPhone);
            return new PhoneAuthResponseDto(false, String.format("인증에 실패하였습니다, 남은 횟수는 %d 회입니다.", 5L - unwrappedPhone.getTried()));
        }
    }
}
