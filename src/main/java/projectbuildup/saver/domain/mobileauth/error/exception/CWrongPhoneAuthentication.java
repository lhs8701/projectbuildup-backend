package projectbuildup.saver.domain.mobileauth.error.exception;

import lombok.Getter;
import projectbuildup.saver.global.error.ErrorCode;

@Getter
public class CWrongPhoneAuthentication extends RuntimeException {
    private ErrorCode errorCode;

    public CWrongPhoneAuthentication() {
        super();
        errorCode = ErrorCode.WRONG_PHONE_AUTHENTICATION;
    }
}
