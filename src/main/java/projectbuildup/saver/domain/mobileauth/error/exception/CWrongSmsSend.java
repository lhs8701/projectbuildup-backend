package projectbuildup.saver.domain.mobileauth.error.exception;

import lombok.Getter;
import projectbuildup.saver.global.error.ErrorCode;

@Getter
public class CWrongSmsSend extends RuntimeException {
    private final ErrorCode errorCode;

    public CWrongSmsSend() {
        super();
        errorCode = ErrorCode.WRONG_SMS_SEND;
    }
}
