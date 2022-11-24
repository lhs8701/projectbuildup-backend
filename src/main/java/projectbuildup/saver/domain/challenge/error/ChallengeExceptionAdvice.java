package projectbuildup.saver.domain.challenge.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import projectbuildup.saver.domain.challenge.error.exception.CChallengeNotFoundException;
import projectbuildup.saver.domain.challenge.error.exception.CUserAlreadyJoinedException;
import projectbuildup.saver.global.common.response.ErrorResponseDto;
import projectbuildup.saver.global.error.ErrorCode;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class ChallengeExceptionAdvice {

    /**
     * 해당 챌린지를 찾을 수 없을 경우 예외 발생
     * @param e CChallengeNotFoundException
     * @return NOT_FOUND 404
     */
    @ExceptionHandler(CChallengeNotFoundException.class)
    protected ResponseEntity<?> handle(CChallengeNotFoundException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }

    /**
     * 이미 해당 챌린지를 진행 중에 참여를 시도할 경우 예외 발생
     * @param e CUserAlreadyJoinedException
     * @return CONFLICT 409
     */
    @ExceptionHandler(CUserAlreadyJoinedException.class)
    protected ResponseEntity<?> handle(CUserAlreadyJoinedException e) {
        ErrorCode errorCode = e.getErrorCode();
        log.error(errorCode.getMessage());
        return new ResponseEntity<>(new ErrorResponseDto(errorCode), errorCode.getStatusCode());
    }
}
