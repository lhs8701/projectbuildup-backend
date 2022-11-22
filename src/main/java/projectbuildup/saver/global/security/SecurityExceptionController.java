package projectbuildup.saver.global.security;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import projectbuildup.saver.domain.auth.basic.error.exception.CExpiredTokenException;
import projectbuildup.saver.domain.auth.basic.error.exception.CUnsupportedTokenException;
import projectbuildup.saver.domain.auth.basic.error.exception.CWrongTypeTokenException;
import projectbuildup.saver.domain.auth.basic.error.exception.CAccessDeniedException;
import projectbuildup.saver.global.error.exception.CAuthenticationEntryPointException;
import projectbuildup.saver.global.error.exception.CIllegalArgumentException;
import springfox.documentation.annotations.ApiIgnore;


@RequiredArgsConstructor
@RestController
@RequestMapping("/exception")
@ApiIgnore
public class SecurityExceptionController {

    @GetMapping("/entrypoint")
    public void entrypointException() {
        throw new CAuthenticationEntryPointException();
    }
    @GetMapping("/entrypoint/illegal")
    public void illegalArgumentException() {
        throw new CIllegalArgumentException();
    }
    @GetMapping("/entrypoint/wrong")
    public void wrongTypeTokenException() {
        throw new CWrongTypeTokenException();
    }
    @GetMapping("/entrypoint/expired")
    public void expiredTokenException() {
        throw new CExpiredTokenException();
    }
    @GetMapping("/entrypoint/unsupported")
    public void unsupportedTokenException() {
        throw new CUnsupportedTokenException();
    }

    @GetMapping("/accessDenied")
    public void accessDeniedException() {
        throw new CAccessDeniedException();
    }
}