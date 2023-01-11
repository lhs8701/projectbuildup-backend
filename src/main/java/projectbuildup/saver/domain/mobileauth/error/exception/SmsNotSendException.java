package projectbuildup.saver.domain.mobileauth.error.exception;

import lombok.Getter;
import projectbuildup.saver.global.error.ErrorCode;

@Getter
public class SmsNotSendException extends RuntimeException {
    private final ErrorCode errorCode;

    public SmsNotSendException() {
        super();
        errorCode = ErrorCode.SMS_NOT_SEND_EXCEPTION;
    }
}
