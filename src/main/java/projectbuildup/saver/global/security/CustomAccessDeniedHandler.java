package projectbuildup.saver.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    // 권한이 없을 경우, AuthorizationException을 처리
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        final Map<String, Object> body = new HashMap<>();
        final ObjectMapper mapper = new ObjectMapper();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        body.put(ResponseField.CODE, ErrorCode.NOT_AUTHORIZED.getCode());
        body.put(ResponseField.MESSAGE, ErrorCode.NOT_AUTHORIZED.getMessage());
        mapper.writeValue(response.getOutputStream(), body);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    }
}
