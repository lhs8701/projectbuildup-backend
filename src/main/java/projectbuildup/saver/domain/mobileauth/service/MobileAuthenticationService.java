package projectbuildup.saver.domain.mobileauth.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.dto.res.PhoneAuthResponseDto;
import projectbuildup.saver.domain.mobileauth.entity.MobileAuthentication;
import projectbuildup.saver.domain.mobileauth.error.exception.*;
import projectbuildup.saver.domain.mobileauth.repository.MobileAuthenticationJpaRepository;

import javax.transaction.Transactional;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.Random;

import java.util.regex.Pattern;

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
public class MobileAuthenticationService {

    MobileAuthenticationJpaRepository mobileAuthenticationJpaRepository;

    private final Environment env;


    public String makeSignature(String url, String timestamp, String method, String accessKey, String secretKey) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
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
        signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        encodeBase64String = Base64.getEncoder().encodeToString(rawHmac);

        return encodeBase64String;
    }


    public void sendSMS(String from, String to, String code) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        String hostNameUrl = "https://sens.apigw.ntruss.com";     		// + 호스트 URL
        String requestUrl= "/sms/v2/services/";                   		// + 요청 URL
        String requestUrlType = "/messages";                      		// + 요청 URL
        String accessKey = env.getProperty("naver.accessKey");                     	// + 네이버 클라우드 플랫폼 회원에게 발급되는 개인 인증키
        String secretKey = env.getProperty("naver.secretKey");   // + 2차 인증을 위해 서비스마다 할당되는 service secret key
        String serviceId = env.getProperty("naver.serviceId");        // + 프로젝트에 할당된 SMS 서비스 ID
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
            throw new CWrongSmsSend();
        }

        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        br.close();

        System.out.println(response.toString());
    }


    public String getRandomCode() {
        int leftLimit = 48;
        int rightLimit = 57;
        int targetStringLength = 6;
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    @Transactional
    public PhoneAuthResponseDto getNumber(String phoneNumber) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        // 값 검증
        if(!Pattern.matches("[0-9]{11}", phoneNumber)) {
            throw new CWrongPhoneNumberForm();
        }


        // 현재 저장되어 있는 전화번호가 있는지 확인하기 위함.
        Optional<MobileAuthentication> phone = mobileAuthenticationJpaRepository.findByPhoneNumber(phoneNumber);
        String code = getRandomCode();

        // 전화번호가 저장되어 있으면 코드만 변경해주고, 없을시 새로 생성해준다.
        if(phone.isPresent()) {
            MobileAuthentication unwrappedMobileAuthentication = phone.get();
            unwrappedMobileAuthentication.setCode(code);
            unwrappedMobileAuthentication.setTried(0L);
            mobileAuthenticationJpaRepository.save(unwrappedMobileAuthentication);
        } else {
            MobileAuthentication newMobileAuthentication = new MobileAuthentication(phoneNumber, code, 0L);
            mobileAuthenticationJpaRepository.save(newMobileAuthentication);
        }

        sendSMS("01021634980", phoneNumber, code);

        log.info(String.format("%s %s", phoneNumber, code));

        return new PhoneAuthResponseDto(true, "성공했습니다.");
    }

    @Transactional
    public PhoneAuthResponseDto verifyNumber(String phoneNumber, String code) {
        Optional<MobileAuthentication> phone = mobileAuthenticationJpaRepository.findByPhoneNumber(phoneNumber);

        // 값 검증
        if(!Pattern.matches("[0-9]{11}", phoneNumber)) {
            throw new CWrongPhoneNumberForm();
        }
        if(!Pattern.matches("[0-9]{6}", code)) {
            throw new CWrongVerifyCodeForm();
        }

        // 받아온 전화번호에 인증 정보가 있는지 확인
        if(phone.isEmpty()) {
            throw new CWrongPhoneAuthentication();
        }

        MobileAuthentication unwrappedMobileAuthentication = phone.get();

        // 이미 시도를 너무 많이 했으면 거절
        if(unwrappedMobileAuthentication.getTried() >= 5) {
            throw new CIllegalVerifyTry();
        }

        log.info(String.format("%s %s", unwrappedMobileAuthentication.getCode(), code));

        // 인증 확인 혹은 실패
        if(code.equals(unwrappedMobileAuthentication.getCode())) {
            mobileAuthenticationJpaRepository.delete(unwrappedMobileAuthentication);
            return new PhoneAuthResponseDto(true, "성공했습니다.");
        } else {
            unwrappedMobileAuthentication.setTried(unwrappedMobileAuthentication.getTried()+1);
            mobileAuthenticationJpaRepository.save(unwrappedMobileAuthentication);
            throw new CWrongVerifyCode();
        }
    }
}
