package projectbuildup.saver.domain.mobileauth.error.exception;

import lombok.Getter;
import projectbuildup.saver.global.error.ErrorCode;

@Getter
public class CWrongPhoneNumberForm extends RuntimeException {
    private final ErrorCode errorCode;

    public CWrongPhoneNumberForm() {
        super();
        errorCode = ErrorCode.WRONG_PHONE_NUMBER_FORM;
    }
}
