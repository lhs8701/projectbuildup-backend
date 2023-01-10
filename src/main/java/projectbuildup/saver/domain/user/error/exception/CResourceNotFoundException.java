package projectbuildup.saver.domain.user.error.exception;


import lombok.Getter;
import projectbuildup.saver.global.error.ErrorCode;

@Getter
public class CResourceNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;
    public CResourceNotFoundException(){
        super();
        this.errorCode = ErrorCode.USER_NOT_FOUND_EXCEPTION;
    }
}
