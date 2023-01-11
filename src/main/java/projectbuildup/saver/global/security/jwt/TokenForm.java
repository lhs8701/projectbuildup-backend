package projectbuildup.saver.global.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class TokenForm {
    String grantType;
    String accessToken;
    String refreshToken;
}
