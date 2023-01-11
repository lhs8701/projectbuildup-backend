package projectbuildup.saver.domain.mobileauth.error.exception;

import lombok.Getter;
import projectbuildup.saver.global.error.ErrorCode;

@Getter
public class CIllegalVerifyTry extends RuntimeException{
    private ErrorCode errorCode;

    public CIllegalVerifyTry() {
        super();
        errorCode = ErrorCode.ILLEGAL_VERIFY_TRY;
    }
}

