package projectbuildup.saver.domain.challenge.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import projectbuildup.saver.domain.challenge.error.exception.CChallengeNotFoundException;

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
        log.error(e.getErrorCode().getMessage());
        return new ResponseEntity<>(e.getErrorCode(), HttpStatus.NOT_FOUND);
    }
}
