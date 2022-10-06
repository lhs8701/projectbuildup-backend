package projectbuildup.saver.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import projectbuildup.saver.global.error.ErrorCode;

@AllArgsConstructor
@Getter
public class CIllegalArgumentException extends RuntimeException {
    private final ErrorCode errorCode;

    public CIllegalArgumentException() {
        super();
        errorCode = ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION;
    }
}
