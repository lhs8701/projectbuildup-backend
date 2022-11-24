package projectbuildup.saver.domain.auth.basic.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import projectbuildup.saver.domain.auth.basic.error.exception.*;
import projectbuildup.saver.domain.auth.basic.error.exception.CWrongPasswordException;
import projectbuildup.saver.domain.auth.basic.error.exception.CAccessDeniedException;
import projectbuildup.saver.global.error.ErrorCode;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class AuthExceptionAdvice {

    /**
     * 패스워드가 불일치할 경우 발생시키는 예외
     */
    @ExceptionHandler(CWrongPasswordException.class)
    protected ResponseEntity<?> handle(CWrongPasswordException e) {
        ErrorCode errorCode = e.getErrorCode();
        return new ResponseEntity<>(errorCode, errorCode.getStatusCode());
    }

    /**
     * Security - JWT 서명이 잘못되었을 때 발생시키는 예외
     */
    @ExceptionHandler(CWrongTypeTokenException.class)
    protected ResponseEntity<?> handle(CWrongTypeTokenException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(errorCode, errorCode.getStatusCode());
    }

    /**
     * Security - 토큰이 만료되었을 때 발생시키는 예외
     */
    @ExceptionHandler(CExpiredTokenException.class)
    protected ResponseEntity<?> handle(CExpiredTokenException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(errorCode, errorCode.getStatusCode());
    }

    /**
     * Security - 지원하지 않는 토큰일 때 발생시키는 예외
     */
    @ExceptionHandler(CUnsupportedTokenException.class)
    protected ResponseEntity<?> handle(CUnsupportedTokenException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(errorCode, errorCode.getStatusCode());
    }

    /**
     * 리프레시 토큰이 불일치할 경우 발생시키는 예외
     */
    @ExceptionHandler(CWrongRefreshTokenException.class)
    protected ResponseEntity<?> handle(CWrongRefreshTokenException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(errorCode, errorCode.getStatusCode());
    }


    /**
     * refresh token이 잘못되었을 경우 발생시키는 예외
     */
    @ExceptionHandler(CRefreshTokenInvalidException.class)
    protected ResponseEntity<?> handle(CRefreshTokenInvalidException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(errorCode, errorCode.getStatusCode());
    }

    /**
     * 리프레쉬 토큰이 만료되었을 경우 발생시키는 예외
     */
    @ExceptionHandler(CRefreshTokenExpiredException.class)
    protected ResponseEntity<?> handle(CRefreshTokenExpiredException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(errorCode, errorCode.getStatusCode());
    }

    /**
     * Security - 권한이 없는 리소스를 요청한 경우 발생시키는 예외
     */
    @ExceptionHandler(CAccessDeniedException.class)
    protected ResponseEntity<?> handle(CAccessDeniedException e) {
        ErrorCode errorCode = e.getErrorCode();
        return new ResponseEntity<>(errorCode, errorCode.getStatusCode());
    }

}
