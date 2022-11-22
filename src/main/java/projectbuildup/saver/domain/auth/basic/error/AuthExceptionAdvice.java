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

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class AuthExceptionAdvice {

    /**
     * 패스워드가 불일치할 경우 발생시키는 예외
     */
    @ExceptionHandler(CWrongPasswordException.class)
    protected ResponseEntity<?> handle(CWrongPasswordException e) {
        return new ResponseEntity<>(e.getErrorCode(), HttpStatus.NOT_FOUND);
    }

    /**
     * Security - JWT 서명이 잘못되었을 때 발생시키는 예외
     */
    @ExceptionHandler(CWrongTypeTokenException.class)
    protected ResponseEntity<?> handle(CWrongTypeTokenException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(e.getErrorCode(), HttpStatus.NOT_FOUND);
    }

    /**
     * Security - 토큰이 만료되었을 때 발생시키는 예외
     */
    @ExceptionHandler(CExpiredTokenException.class)
    protected ResponseEntity<?> handle(CExpiredTokenException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(e.getErrorCode(), HttpStatus.NOT_FOUND);
    }

    /**
     * Security - 지원하지 않는 토큰일 때 발생시키는 예외
     */
    @ExceptionHandler(CUnsupportedTokenException.class)
    protected ResponseEntity<?> handle(CUnsupportedTokenException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(e.getErrorCode(), HttpStatus.NOT_FOUND);
    }

    /**
     * 리프레시 토큰이 불일치할 경우 발생시키는 예외
     */
    @ExceptionHandler(CWrongRefreshTokenException.class)
    protected ResponseEntity<?> handle(CWrongRefreshTokenException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(e.getErrorCode(), HttpStatus.NOT_FOUND);
    }


    /**
     * refresh token이 잘못되었을 경우 발생시키는 예외
     */
    @ExceptionHandler(CRefreshTokenInvalidException.class)
    protected ResponseEntity<?> handle(CRefreshTokenInvalidException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(e.getErrorCode(), HttpStatus.NOT_FOUND);
    }

    /**
     * 리프레쉬 토큰이 만료되었을 경우 발생시키는 예외
     */
    @ExceptionHandler(CRefreshTokenExpiredException.class)
    protected ResponseEntity<?> handle(CRefreshTokenExpiredException e) {
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(e.getErrorCode(), HttpStatus.NOT_FOUND);
    }

    /**
     * Security - 권한이 없는 리소스를 요청한 경우 발생시키는 예외
     */
    @ExceptionHandler(CAccessDeniedException.class)
    protected ResponseEntity<?> handle(CAccessDeniedException e) {
        return new ResponseEntity<>(e.getErrorCode(), HttpStatus.NOT_FOUND);
    }

}
