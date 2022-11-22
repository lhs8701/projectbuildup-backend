package projectbuildup.saver.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND_EXCEPTION(1001, "해당 회원을 조회할 수 없습니다."),
    WRONG_PASSWORD_EXCEPTION(1002, "잘못된 비밀번호 입니다."),

    REFRESH_TOKEN_INVALID_EXCEPTION(1003, "리프레쉬 토큰이 잘못되었습니다."),
    REFRESH_TOKEN_EXPIRED_EXCPEPTION(1004, "리프레쉬 토큰이 만료되었습니다. 로그인을 다시 해주세요."),
    AUTHENTICATION_ENTRY_POINT_EXCEPTION(1005, "해당 리소스에 접근하기 위한 권한이 없습니다."), //정상적으로 Jwt이 제대로 오지 않은 경우
    ACCESS_DENIED(1006, "해당 리소스에 접근할 수 없는 권한입니다."),
    USER_EXIST_EXCEPTION(1007, "이미 가입된 계정입니다. 로그인을 해주세요"),

    CHALLENGE_NOT_FOUND_EXCEPTION(1008, "해당 챌린지를 조회할 수 없습니다."),

    WRONG_REFRESH_TOKEN_EXCEPTION(9000, "refresh 토큰이 잘못되었습니다"),
    WRONG_TYPE_TOKEN_EXCEPTION(9001, "잘못된 Jwt 서명입니다."),
    EXPIRED_TOKEN_EXCEPTION(9002, "만료된 토큰입니다."),
    UNSUPPORTED_TOKEN_EXCEPTION(9003, "지원하지 않는 토큰입니다."),
    ILLEGAL_ARGUMENT_EXCEPTION(9004, "잘못된 형식입니다."),


    WRONG_APPROACH(-9998, "잘못된 접근입니다."),
    INTERNAL_SERVER_ERROR(-9999, "서버 에러입니다.");

    private int code;
    private String message;
}
