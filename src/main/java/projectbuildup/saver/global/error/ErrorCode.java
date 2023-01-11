package projectbuildup.saver.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 비즈니스 영역 */
    //1
    USER_NOT_FOUND_EXCEPTION(-1000, "해당 회원을 조회할 수 없습니다.", HttpStatus.NOT_FOUND),
    CHALLENGE_NOT_FOUND_EXCEPTION(-1001, "해당 챌린지를 조회할 수 없습니다.", HttpStatus.NOT_FOUND),
    FILE_NOT_FOUND_EXCEPTION(-1008, "해당 파일을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    USER_ALREADY_JOINED_EXCEPTION(-1009, "이미 참여 중인 챌린지 입니다.", HttpStatus.CONFLICT),
    RECENT_SAVING_NOT_FOUND_EXCEPTION(-1010, "최근 절약 내역이 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    PARTICIPATION_NOT_FOUND_EXCEPTION(-1011, "해당 챌린지에 참여하고 있지 않습니다.", HttpStatus.NOT_FOUND),


    //2
    WRONG_PASSWORD_EXCEPTION(-3000, "잘못된 비밀번호 입니다.", HttpStatus.BAD_REQUEST),
    USER_EXIST_EXCEPTION(-3003, "이미 가입된 계정입니다. 로그인을 해주세요", HttpStatus.CONFLICT),


    /* 권한 & 인증 영역 */
    REFRESH_TOKEN_INVALID_EXCEPTION(-5001, "리프레쉬 토큰이 잘못되었습니다.", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_EXPIRED_EXCEPTION(-5002, "리프레쉬 토큰이 만료되었습니다. 로그인을 다시 해주세요.", HttpStatus.UNAUTHORIZED),
    AUTHENTICATION_ENTRY_POINT_EXCEPTION(-5003, "해당 리소스에 접근하기 위한 권한이 없습니다.", HttpStatus.FORBIDDEN),
    ACCESS_DENIED(-5004, "해당 리소스에 접근할 수 없는 권한입니다.", HttpStatus.FORBIDDEN),
    WRONG_REFRESH_TOKEN_EXCEPTION(-5005, "refresh 토큰이 잘못되었습니다", HttpStatus.UNAUTHORIZED),
    WRONG_TYPE_TOKEN_EXCEPTION(-5006, "잘못된 Jwt 서명입니다.", HttpStatus.UNAUTHORIZED),
    EXPIRED_TOKEN_EXCEPTION(-5007, "만료된 토큰입니다.", HttpStatus.UNAUTHORIZED),
    UNSUPPORTED_TOKEN_EXCEPTION(-5008, "지원하지 않는 토큰입니다.", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_EXPIRED_EXCPEPTION(-5009, "리프레시 토큰이 만료되었습니다. 다시 로그인해주세요.", HttpStatus.FORBIDDEN),

    /* 전화번호 인증 */
    WRONG_SMS_SEND(-6000, "문자가 전송중 오류가 발생했습니다.", HttpStatus.BAD_GATEWAY),
    WRONG_PHONE_NUMBER_FORM(-6001, "전화번호 양식이 잘못되었습니다.", HttpStatus.BAD_REQUEST),
    WRONG_PHONE_AUTHENTICATION(-6002, "인증중이 아닙니다, 인증버튼을 새로 눌러주세요", HttpStatus.BAD_REQUEST),
    ILLEGAL_VERIFY_TRY(-6003, "인증 횟수가 초과되었습니다, 새로 코드를 발급해주세요.", HttpStatus.BAD_REQUEST),
    WRONG_VERIFY_CODE_FORM(-6004, "인증코드 양식이 잘못되었습니다.", HttpStatus.BAD_REQUEST),
    WRONG_VERIFY_CODE(-6005, "인증번호가 잘못되었습니다", HttpStatus.BAD_REQUEST),


    /* 외부 영역 */
    ILLEGAL_ARGUMENT_EXCEPTION(-7000, "잘못된 형식입니다.", HttpStatus.BAD_REQUEST),
    WRONG_APPROACH(-7001, "잘못된 접근입니다.", HttpStatus.BAD_REQUEST),
    WRONG_FILE_TYPE_EXCEPTION(-7002, "올바르지 않은 파일 형식입니다.", HttpStatus.BAD_REQUEST),


    /* 서버 오류 */
    INTERNAL_SERVER_ERROR(-9999, "서버 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private int code;
    private String message;
    private HttpStatus statusCode;
}
