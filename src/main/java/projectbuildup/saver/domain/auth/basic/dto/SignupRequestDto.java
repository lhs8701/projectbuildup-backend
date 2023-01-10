package projectbuildup.saver.domain.auth.basic.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import projectbuildup.saver.domain.user.entity.Member;

import java.util.Collections;

@Getter
@NoArgsConstructor
public class SignupRequestDto {
    private String idToken;
    private String password;
    private String nickName;

    private String phoneNumber;

    public Member toEntity(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .idToken(this.idToken)
                .nickName(this.nickName)
                .phoneNumber(this.phoneNumber)
                .password(passwordEncoder.encode(password))
                .nickName(nickName)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }
}