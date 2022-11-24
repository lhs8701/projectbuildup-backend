package projectbuildup.saver.domain.auth.basic.error.exception;

import lombok.Getter;
import projectbuildup.saver.global.error.ErrorCode;

@Getter
public class CWrongTypeTokenException extends RuntimeException{
    private final ErrorCode errorCode;
    public CWrongTypeTokenException(){
        super();
        this.errorCode = ErrorCode.WRONG_TYPE_TOKEN_EXCEPTION;
    }
}
