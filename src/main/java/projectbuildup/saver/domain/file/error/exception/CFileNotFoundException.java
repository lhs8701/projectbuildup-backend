package projectbuildup.saver.domain.file.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import projectbuildup.saver.global.error.ErrorCode;

@Slf4j
@Getter
@AllArgsConstructor
public class CFileNotFoundException extends RuntimeException { //정상적으로 Jwt이 제대로 오지 않은 경우
    private final ErrorCode errorCode;

    public CFileNotFoundException() {
        super();
        errorCode = ErrorCode.FILE_NOT_FOUND_EXCEPTION;
    }
}



