package projectbuildup.saver.domain.recentsaving.error.exception;

import lombok.Getter;
import projectbuildup.saver.global.error.ErrorCode;

@Getter
public class CRecentSavingNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public CRecentSavingNotFoundException(){
        super();
        errorCode = ErrorCode.RECENT_SAVING_NOT_FOUND_EXCEPTION;
    }
}
