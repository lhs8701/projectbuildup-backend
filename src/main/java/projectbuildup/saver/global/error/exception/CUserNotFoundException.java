package projectbuildup.saver.global.error.exception;


import lombok.Getter;
import projectbuildup.saver.global.error.ErrorCode;

@Getter
public class CUserNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;
    public CUserNotFoundException(){
        super();
        this.errorCode = ErrorCode.USER_NOT_FOUND_EXCEPTION;
    }
}
