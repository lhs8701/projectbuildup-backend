package projectbuildup.saver.domain.mobileauth.error.exception;

import lombok.Getter;
import projectbuildup.saver.global.error.ErrorCode;

@Getter
public class CWrongVerifyCodeForm extends RuntimeException {
    private ErrorCode errorCode;
    public CWrongVerifyCodeForm() {
        super();
        errorCode = ErrorCode.WRONG_VERIFY_CODE_FORM;
    }
}
