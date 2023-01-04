package projectbuildup.saver.domain.recentremittance.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import projectbuildup.saver.domain.recentremittance.error.exception.CRecentRemittanceNotFoundException;
import projectbuildup.saver.global.common.response.ErrorResponseDto;
import projectbuildup.saver.global.error.ErrorCode;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class RecentRemittanceExceptionAdvice {

    @ExceptionHandler(CRecentRemittanceNotFoundException.class)
    protected ResponseEntity<?> handle(CRecentRemittanceNotFoundException e) {
        ErrorCode errorCode = e.getErrorCode();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }
}
