package projectbuildup.saver.domain.mobileauth.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import projectbuildup.saver.domain.mobileauth.error.exception.SmsNotSendException;
import projectbuildup.saver.global.common.response.ErrorResponseDto;
import projectbuildup.saver.global.error.ErrorCode;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class PhoneAuthExceptionAdvice {
    /**
     * SMS 전송 실패
     */
    @ExceptionHandler(SmsNotSendException.class)
    protected ResponseEntity<?> handle(SmsNotSendException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }
}
