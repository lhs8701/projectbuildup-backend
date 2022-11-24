package projectbuildup.saver.global.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import projectbuildup.saver.global.common.response.ErrorResponseDto;
import projectbuildup.saver.global.error.exception.CIllegalArgumentException;
import projectbuildup.saver.global.error.exception.CWrongApproachException;
import projectbuildup.saver.domain.file.error.exception.CWrongFileTypeException;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * 공통 서버 에러
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> defaultException(Exception e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 잘못된 형식일 때 발생시키는 예외
     */
    @ExceptionHandler(CIllegalArgumentException.class)
    protected ResponseEntity<?> handle(CIllegalArgumentException e) {
        ErrorCode errorCode = e.getErrorCode();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    /**
     * 잘못된 접근시 발생시키는 예외
     */
    @ExceptionHandler(CWrongApproachException.class)
    protected ResponseEntity<?> handle(CWrongApproachException e) {
        ErrorCode errorCode = e.getErrorCode();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    /**
     * 파일 형식이 잘못되었을 경우 발생하는 예외
     */
    @ExceptionHandler(CWrongFileTypeException.class)
    protected ResponseEntity<?> handle(CWrongFileTypeException e) {
        ErrorCode errorCode = e.getErrorCode();
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }
}
