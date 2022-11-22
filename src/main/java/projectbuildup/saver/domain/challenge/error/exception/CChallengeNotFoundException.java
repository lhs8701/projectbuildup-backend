package projectbuildup.saver.domain.challenge.error.exception;


import lombok.Getter;
import projectbuildup.saver.global.error.ErrorCode;

@Getter
public class CChallengeNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;
    public CChallengeNotFoundException(){
        super();
        this.errorCode = ErrorCode.CHALLENGE_NOT_FOUND_EXCEPTION;
    }
}
