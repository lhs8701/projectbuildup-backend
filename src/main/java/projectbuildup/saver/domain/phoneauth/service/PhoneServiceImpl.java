package projectbuildup.saver.domain.phoneauth.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.dto.res.PhoneAuthResponseDto;
import projectbuildup.saver.domain.phoneauth.entity.PhoneEntity;
import projectbuildup.saver.domain.phoneauth.repository.PhoneRepository;

import javax.transaction.Transactional;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.Random;

import java.util.regex.Pattern;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;


@Service
@AllArgsConstructor
@Slf4j
public class PhoneServiceImpl implements PhoneService{

    PhoneRepository phoneRepository;


    @Override
    public String makeSignature(String url, String timestamp, String method, String accessKey, String secretKey) {
        String space = " ";                    // one space
        String newLine = "\n";                 // new line


        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey;
        String encodeBase64String;
        try {

            signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
            encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            encodeBase64String = e.toString();
        } catch (NoSuchAlgorithmException e) {
            encodeBase64String = e.toString();
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }


        return encodeBase64String;
    }

    @Override
    public void sendSMS(String to, String code) {
        String from = "01021634980";                                    // 발신번호
        String hostNameUrl = "https://sens.apigw.ntruss.com";     		// + 호스트 URL
        String requestUrl= "/sms/v2/services/";                   		// + 요청 URL
        String requestUrlType = "/messages";                      		// + 요청 URL
        String accessKey = "kEIEdgWRluUtJXy1E51R";                     	// + 네이버 클라우드 플랫폼 회원에게 발급되는 개인 인증키
        String secretKey = "AtzSAHcUZNflEDYbKSClDluSIMaXOLFE4xYMUPrX";  // + 2차 인증을 위해 서비스마다 할당되는 service secret key
        String serviceId = "ncp:sms:kr:293656383759:saver_phone";       // + 프로젝트에 할당된 SMS 서비스 ID
        String method = "POST";											// + 요청 method
        String timestamp = Long.toString(System.currentTimeMillis()); 	// + current timestamp (epoch)
        requestUrl += serviceId + requestUrlType;
        String apiUrl = hostNameUrl + requestUrl;

        // JSON 을 활용한 body data 생성
        JSONObject bodyJson = new JSONObject();
        JSONObject toJson = new JSONObject();
        JSONArray  toArr = new JSONArray();

        toJson.put("to", to);						// + Mandatory(필수), messages.to	수신번호, -를 제외한 숫자만 입력 가능
        toArr.put(toJson);

        bodyJson.put("type","SMS");							// + Madantory, 메시지 Type (SMS | LMS | MMS), (소문자 가능)
        bodyJson.put("from",from);					// + Mandatory, 발신번호, 사전 등록된 발신번호만 사용 가능
        bodyJson.put("content","번호인증: 인증번호는 [" + code + "] 입니다.");	// + Mandatory(필수), 기본 메시지 내용, SMS: 최대 80byte, LMS, MMS: 최대 2000byte
        bodyJson.put("messages", toArr);					// + Mandatory(필수), 아래 항목들 참조 (messages.XXX), 최대 1,000개

        String body = bodyJson.toString();

        System.out.println(body);

        try {
            URL url = new URL(apiUrl);

            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestProperty("content-type", "application/json");
            con.setRequestProperty("x-ncp-apigw-timestamp", timestamp);
            con.setRequestProperty("x-ncp-iam-access-key", accessKey);
            con.setRequestProperty("x-ncp-apigw-signature-v2", makeSignature(requestUrl, timestamp, method, accessKey, secretKey));
            con.setRequestMethod(method);
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());

            wr.write(body.getBytes());
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            BufferedReader br;
            System.out.println("responseCode" +" " + responseCode);
            if(responseCode == 202) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else { // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }

            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            System.out.println(response.toString());

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public String getRandomCode() {
        int leftLimit = 48;
        int rightLimit = 57;
        int targetStringLength = 6;
        Random random = new Random();
        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
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

            sendSMS(phoneNumber, code);

            log.info(String.format("%s %s", phoneNumber, code));
        } catch (Exception e) {
            return new PhoneAuthResponseDto(false, "에러가 발생했습니다.");
        }

        return new PhoneAuthResponseDto(true, "성공했습니다.");
    }

    @Override
    @Transactional
    public PhoneAuthResponseDto verifyNumber(String phoneNumber, String code) {
        try {
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
        } catch (Exception e) {
            return new PhoneAuthResponseDto(false, "에러가 발생했습니다.");
        }
    }
}
