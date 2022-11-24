package projectbuildup.saver.domain.challenge.error.exception;


import lombok.Getter;
import projectbuildup.saver.global.error.ErrorCode;

@Getter
public class CUserAlreadyJoinedException extends RuntimeException{
    private final ErrorCode errorCode;
    public CUserAlreadyJoinedException(){
        super();
        this.errorCode = ErrorCode.USER_ALREADY_JOINED_EXCEPTION;
    }
}
