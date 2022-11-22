package projectbuildup.saver.domain.auth.basic.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import projectbuildup.saver.global.error.ErrorCode;

@AllArgsConstructor
@Getter
public class CUnsupportedTokenException extends RuntimeException {
    private final ErrorCode errorCode;

    public CUnsupportedTokenException() {
        super();
        errorCode = ErrorCode.UNSUPPORTED_TOKEN_EXCEPTION;
    }
}
