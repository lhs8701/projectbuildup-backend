package projectbuildup.saver.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import projectbuildup.saver.global.constant.ResponseField;
import projectbuildup.saver.global.error.ErrorCode;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        final Map<String, Object> body = new HashMap<>();
        final ObjectMapper mapper = new ObjectMapper();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        body.put(ResponseField.CODE, ErrorCode.AUTHENTICATION_ERROR.getCode());
        body.put(ResponseField.MESSAGE, ErrorCode.AUTHENTICATION_ERROR.getMessage());
        mapper.writeValue(response.getOutputStream(), body);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
