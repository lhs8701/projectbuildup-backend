package projectbuildup.saver.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "MIVV API 명세서",
                description = "MIVV의 API 명세서입니다.",
                version = "0.0.1",
                contact = @Contact(
                        name = "hansol",
                        email = "hansol8701@naver.com"
                )
        )
)
@Configuration
public class SpringDocConfig {

}