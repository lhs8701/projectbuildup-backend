package projectbuildup.saver.domain.recentremittance.error.exception;

import lombok.Getter;
import projectbuildup.saver.global.error.ErrorCode;

@Getter
public class CRecentRemittanceNotFoundException extends RuntimeException{
    private final ErrorCode errorCode;

    public CRecentRemittanceNotFoundException(){
        super();
        errorCode = ErrorCode.RECENT_SAVING_NOT_FOUND_EXCEPTION;
    }
}
