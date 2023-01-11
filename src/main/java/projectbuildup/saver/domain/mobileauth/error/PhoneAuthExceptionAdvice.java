package projectbuildup.saver.domain.mobileauth.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import projectbuildup.saver.domain.mobileauth.error.exception.*;
import projectbuildup.saver.global.common.response.ErrorResponseDto;
import projectbuildup.saver.global.error.ErrorCode;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class PhoneAuthExceptionAdvice {

    /**
     * 인증 가능 횟수 초과
     */
    @ExceptionHandler(CIllegalVerifyTry.class)
    protected ResponseEntity<?> handle(CIllegalVerifyTry e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    /**
     * 인증 중이 아닌데 코드를 보냈을떄
     */
    @ExceptionHandler(CWrongPhoneAuthentication.class)
    protected ResponseEntity<?> handle(CWrongPhoneAuthentication e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    /**
     * 전화번호 양식이 잘못 되었을떄
     */
    @ExceptionHandler(CWrongPhoneNumberForm.class)
    protected ResponseEntity<?> handle(CWrongPhoneNumberForm e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    /**
     * SMS 전송 실패
     */
    @ExceptionHandler(CWrongSmsSend.class)
    protected ResponseEntity<?> handle(CWrongSmsSend e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    /**
     * 인증 코드가 잘못 되었을때
     */
    @ExceptionHandler(CWrongVerifyCode.class)
    protected ResponseEntity<?> handle(CWrongVerifyCode e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    /**
     * 인증 코드양식이 잘못 되었을때
     */
    @ExceptionHandler(CWrongVerifyCodeForm.class)
    protected ResponseEntity<?> handle(CWrongVerifyCodeForm e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

}
