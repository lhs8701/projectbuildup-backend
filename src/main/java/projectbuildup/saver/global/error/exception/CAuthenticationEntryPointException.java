package projectbuildup.saver.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import projectbuildup.saver.global.error.ErrorCode;

@Slf4j
@Getter
@AllArgsConstructor
public class CAuthenticationEntryPointException extends RuntimeException { //정상적으로 Jwt이 제대로 오지 않은 경우
    private final ErrorCode errorCode;

    public CAuthenticationEntryPointException() {
        super();
        errorCode = ErrorCode.AUTHENTICATION_ENTRY_POINT_EXCEPTION;
    }
}



