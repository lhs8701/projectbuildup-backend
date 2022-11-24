package projectbuildup.saver.domain.auth.basic.error.exception;

import lombok.Getter;
import projectbuildup.saver.global.error.ErrorCode;

@Getter
public class CWrongPasswordException extends RuntimeException{
    private final ErrorCode errorCode;
    public CWrongPasswordException(){
        super();
        errorCode = ErrorCode.WRONG_PASSWORD_EXCEPTION;
    }
}
