package projectbuildup.saver.domain.auth.basic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import projectbuildup.saver.domain.user.entity.Member;

import java.util.Collections;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequestDto {
    private String idToken;
    private String password;
    private String nickname;

    private String phoneNumber;

    public Member toEntity(String encodedPassword) {
        return Member.builder()
                .idToken(this.idToken)
                .nickName(this.nickname)
                .phoneNumber(this.phoneNumber)
                .password(encodedPassword)
                .nickName(nickname)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }
}