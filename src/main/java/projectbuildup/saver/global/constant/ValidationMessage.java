package projectbuildup.saver.global.constant;

import lombok.Getter;

@Getter
public class ValidationMessage {
    public final static String NOT_VALID_EMAIL = "계정은 이메일 형식이어야 합니다.";
    public static final String NOT_VALID_BIRTH_DATE = "잘못된 생년월일 형식입니다.";
    public static final String NOT_VALID_PASSWORD = "비밀번호로는 8~16자의 영문, 최소 하나의 숫자와 특수문자를 사용하세요.";
    public static final String NOT_VALID_NICKNAME = "닉네임은 2~16자여야 합니다.";
    public static final String NOT_VALID_MOBILE = "잘못된 전화번호 형식입니다.";
}
