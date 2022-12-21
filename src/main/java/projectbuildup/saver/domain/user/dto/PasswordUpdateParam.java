package projectbuildup.saver.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PasswordUpdateParam {
    String idToken;
    String password;
}
