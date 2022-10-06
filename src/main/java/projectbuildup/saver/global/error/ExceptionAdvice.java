package projectbuildup.saver.global.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import projectbuildup.saver.global.error.exception.*;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * 공통 서버 에러
     */
//    @ExceptionHandler(Exception.class)
//    protected CommonResult defaultException(Exception e) {
//        return responseService.getFailResult(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), ErrorCode.INTERNAL_SERVER_ERROR.getMessage());
//    }

    /**
     * 잘못된 형식일 때 발생시키는 예외
     */
    @ExceptionHandler(CIllegalArgumentException.class)
    protected ResponseEntity<?> handle(CIllegalArgumentException e) {
        return new ResponseEntity<>(ErrorResponse.of(e.getErrorCode()), HttpStatus.NOT_FOUND);
    }

    /**
     * Security - 권한이 없는 리소스를 요청한 경우 발생시키는 예외
     */
    @ExceptionHandler(CAccessDeniedException.class)
    protected ResponseEntity<?> handle(CAccessDeniedException e) {
        return new ResponseEntity<>(ErrorResponse.of(e.getErrorCode()), HttpStatus.NOT_FOUND);
    }

    /**
     * Security - JWT 서명이 잘못되었을 때 발생시키는 예외
     */
    @ExceptionHandler(CWrongTypeTokenException.class)
    protected ResponseEntity<?> handle(CWrongTypeTokenException e) {
        return new ResponseEntity<>(ErrorResponse.of(e.getErrorCode()), HttpStatus.NOT_FOUND);
    }
    /**
     * Security - 토큰이 만료되었을 때 발생시키는 예외
     */
    @ExceptionHandler(CExpiredTokenException.class)
    protected ResponseEntity<?> handle(CExpiredTokenException e) {
        return new ResponseEntity<>(ErrorResponse.of(e.getErrorCode()), HttpStatus.NOT_FOUND);
    }
    /**
     * Security - 지원하지 않는 토큰일 때 발생시키는 예외
     */
    @ExceptionHandler(CUnsupportedTokenException.class)
    protected ResponseEntity<?> handle(CUnsupportedTokenException e) {
        return new ResponseEntity<>(ErrorResponse.of(e.getErrorCode()), HttpStatus.NOT_FOUND);
    }

    /**
     * 리프레시 토큰이 불일치할 경우 발생시키는 예외
     */
    @ExceptionHandler(CWrongRefreshTokenException.class)
    protected ResponseEntity<?> handle(CWrongRefreshTokenException e) {
        return new ResponseEntity<>(ErrorResponse.of(e.getErrorCode()), HttpStatus.NOT_FOUND);
    }

    /**
     * 잘못된 접근시 발생시키는 예외
     */
    @ExceptionHandler(CWrongApproachException.class)
    protected ResponseEntity<?> handle(CWrongApproachException e) {
        return new ResponseEntity<>(ErrorResponse.of(e.getErrorCode()), HttpStatus.NOT_FOUND);
    }

    /**
     * 해당 유저를 찾을 수 없을 경우 발생시키는 예외
     */
    @ExceptionHandler(CUserNotFoundException.class)
    protected ResponseEntity<?> handle(CUserNotFoundException e) {
        return new ResponseEntity<>(ErrorResponse.of(e.getErrorCode()), HttpStatus.NOT_FOUND);
    }

    /**
     * 패스워드가 불일치할 경우 발생시키는 예외
     */
    @ExceptionHandler(CWrongPasswordException.class)
    protected ResponseEntity<?> handle(CWrongPasswordException e) {
        return new ResponseEntity<>(ErrorResponse.of(e.getErrorCode()), HttpStatus.NOT_FOUND);
    }

    /**
     * refresh token이 잘못되었을 경우 발생시키는 예외
     */
    @ExceptionHandler(CRefreshTokenInvalidException.class)
    protected ResponseEntity<?> handle(CRefreshTokenInvalidException e) {
        return new ResponseEntity<>(ErrorResponse.of(e.getErrorCode()), HttpStatus.NOT_FOUND);
    }

    /**
     * 리프레쉬 토큰이 만료되었을 경우 발생시키는 예외
     */
    @ExceptionHandler(CRefreshTokenExpiredException.class)
    protected ResponseEntity<?> handle(CRefreshTokenExpiredException e) {
        return new ResponseEntity<>(ErrorResponse.of(e.getErrorCode()), HttpStatus.NOT_FOUND);
    }

    /***
     * 해당 계정이 이미 가입되어 있는 경우 발생시키는 예외
     */
    @ExceptionHandler(CUserExistException.class)
    protected ResponseEntity<?> handle(CUserExistException e) {
        return new ResponseEntity<>(ErrorResponse.of(e.getErrorCode()), HttpStatus.NOT_FOUND);
    }
}
