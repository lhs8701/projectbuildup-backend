package projectbuildup.saver.domain.auth.basic.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import projectbuildup.saver.global.error.ErrorCode;

@AllArgsConstructor
@Getter
public class CExpiredTokenException extends RuntimeException {
    private final ErrorCode errorCode;

    public CExpiredTokenException() {
        super();
        errorCode = ErrorCode.EXPIRED_TOKEN_EXCEPTION;
    }
}
