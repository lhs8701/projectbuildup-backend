package projectbuildup.saver.domain.auth.basic.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import projectbuildup.saver.global.error.ErrorCode;

@Getter
@AllArgsConstructor
public class CRefreshTokenExpiredException extends RuntimeException{
    private final ErrorCode errorCode;
    public CRefreshTokenExpiredException(){
        super();
        errorCode = ErrorCode.REFRESH_TOKEN_EXPIRED_EXCPEPTION;
    }
}
