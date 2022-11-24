package projectbuildup.saver.domain.user.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import projectbuildup.saver.domain.user.error.exception.CUserExistException;
import projectbuildup.saver.domain.user.error.exception.CUserNotFoundException;
import projectbuildup.saver.global.common.response.ErrorResponseDto;
import projectbuildup.saver.global.error.ErrorCode;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class UserExceptionAdvice {

    /**
     * 해당 유저를 찾을 수 없을 경우 발생시키는 예외
     */
    @ExceptionHandler(CUserNotFoundException.class)
    protected ResponseEntity<?> handle(CUserNotFoundException e) {
        ErrorCode errorCode = e.getErrorCode();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    /***
     * 해당 계정이 이미 가입되어 있는 경우 발생시키는 예외
     */
    @ExceptionHandler(CUserExistException.class)
    protected ResponseEntity<?> handle(CUserExistException e) {
        ErrorCode errorCode = e.getErrorCode();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }
}
