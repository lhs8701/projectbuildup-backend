package projectbuildup.saver.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.*;
import projectbuildup.saver.domain.alarm.entity.AlarmEntity;
import projectbuildup.saver.domain.challengeLog.entity.ChallengeLogEntity;
import projectbuildup.saver.domain.saving.entity.SavingEntity;
import projectbuildup.saver.global.common.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="User")
public class UserEntity extends BaseTimeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true)
    private String loginId;

    @Column(length = 300)
    private String password;

    @Column(length = 10)
    private String nickName;

    @Column(length = 15)
    private String phoneNumber;

    //이미지 처리에 대해서 추후 얘기해 봐야 할 듯
    @Column(length = 100)
    private String profileImage;

    @ElementCollection(fetch = FetchType.EAGER) //LAZY -> 오류
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChallengeLogEntity> challengeLogEntityList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SavingEntity> savingEntityList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.loginId;
    }

    @Override
    public String getPassword() {
        return this.password;
    }


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AlarmEntity> sendAlarmEntityList;

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AlarmEntity> receiveAlarmEntityList;
}


