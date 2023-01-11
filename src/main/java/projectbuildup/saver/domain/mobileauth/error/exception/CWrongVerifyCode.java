package projectbuildup.saver.domain.mobileauth.error.exception;

import lombok.Getter;
import projectbuildup.saver.global.error.ErrorCode;

@Getter
public class CWrongVerifyCode extends RuntimeException {
    private ErrorCode errorCode;
    public CWrongVerifyCode() {
        super();
        errorCode = ErrorCode.WRONG_VERIFY_CODE;
    }
}
