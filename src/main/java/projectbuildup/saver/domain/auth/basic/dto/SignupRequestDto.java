package projectbuildup.saver.domain.auth.basic.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import projectbuildup.saver.domain.user.entity.UserEntity;

import java.util.Collections;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequestDto {
    private String loginId;
    private String password;

    private String nickName;

    private String phoneNumber;

    public UserEntity toEntity(PasswordEncoder passwordEncoder) {
        return UserEntity.builder()
                .loginId(this.loginId)
                .nickName(this.nickName)
                .phoneNumber(this.phoneNumber)
                .password(passwordEncoder.encode(password))
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
    }
}