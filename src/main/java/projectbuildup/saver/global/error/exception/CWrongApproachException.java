package projectbuildup.saver.global.error.exception;

import lombok.Getter;
import projectbuildup.saver.global.error.ErrorCode;

@Getter
public class CWrongApproachException extends RuntimeException{
    ErrorCode errorCode;

    public CWrongApproachException() {
        super();
        errorCode = ErrorCode.WRONG_APPROACH;
    }
}