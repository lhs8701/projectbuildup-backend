package projectbuildup.saver.domain.user.error.exception;

import lombok.Getter;
import projectbuildup.saver.global.error.ErrorCode;

@Getter
public class CUserExistException extends RuntimeException{
    private final ErrorCode errorCode;
    public CUserExistException(){
        super();
        errorCode = ErrorCode.USER_EXIST_EXCEPTION;
    }
}

