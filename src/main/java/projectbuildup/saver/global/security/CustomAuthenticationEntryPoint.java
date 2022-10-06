package projectbuildup.saver.global.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import projectbuildup.saver.global.error.ErrorCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = String.valueOf(request.getAttribute("exception"));

        //잘못된 토큰
        if (exception.equals(String.valueOf(ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION.getCode()))){
            response.sendRedirect("/exception/entrypoint/illegal");
        }
        //잘못된 서명의 토큰인 경우
       else if(exception.equals(String.valueOf(ErrorCode.WRONG_TYPE_TOKEN_EXCEPTION.getCode()))) {
            response.sendRedirect("/exception/entrypoint/wrong");
        }
        //토큰 만료된 경우
        else if(exception.equals(String.valueOf(ErrorCode.EXPIRED_TOKEN_EXCEPTION.getCode()))) {
            response.sendRedirect("/exception/entrypoint/expired");
        }
        //지원되지 않는 토큰인 경우
        else if(exception.equals(String.valueOf(ErrorCode.UNSUPPORTED_TOKEN_EXCEPTION.getCode()))) {
            response.sendRedirect("/exception/entrypoint/unsupported");
        }
        //접근 권한이 없는 경우
        else {
            response.sendRedirect("/exception/entrypoint");
        }
    }
}
