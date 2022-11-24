package projectbuildup.saver.domain.auth.basic.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import projectbuildup.saver.global.error.ErrorCode;

@Getter
@AllArgsConstructor
public class CAccessDeniedException extends RuntimeException{
    private final ErrorCode errorCode;

    public CAccessDeniedException() {
        super();
        errorCode = ErrorCode.ACCESS_DENIED;
    }
}
