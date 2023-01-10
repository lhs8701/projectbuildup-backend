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



@RequiredArgsConstructor
@RestController
@RequestMapping("/exception")
public class SecurityExceptionController {
    @GetMapping("/entrypoint")
    public void entrypointException() {
        throw new CAuthenticationEntryPointException();
    }
    @GetMapping("/accessDenied")
    public void accessDeniedException() {
        throw new CAccessDeniedException();
    }
}