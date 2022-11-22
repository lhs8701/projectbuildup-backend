package projectbuildup.saver.domain.auth.basic.error.exception;

import lombok.Getter;
import projectbuildup.saver.global.error.ErrorCode;


@Getter
public class CRefreshTokenInvalidException extends RuntimeException {
    private final ErrorCode errorCode;
    public CRefreshTokenInvalidException(){
        super();
        errorCode = ErrorCode.REFRESH_TOKEN_INVALID_EXCEPTION;
    }
}
