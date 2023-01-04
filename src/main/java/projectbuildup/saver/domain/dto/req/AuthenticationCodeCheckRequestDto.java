package projectbuildup.saver.domain.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthenticationCodeCheckRequestDto {
    private String phoneNumber;
    private String code;
}
