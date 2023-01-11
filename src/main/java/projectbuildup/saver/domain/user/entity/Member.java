package projectbuildup.saver.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.*;
import projectbuildup.saver.domain.notification.entity.Notification;
import projectbuildup.saver.domain.participation.entity.Participation;
import projectbuildup.saver.domain.image.entity.Image;
import projectbuildup.saver.domain.ranking.entity.Ranking;
import projectbuildup.saver.domain.recentremittance.entity.RecentRemittance;
import projectbuildup.saver.domain.remittance.entity.Remittance;
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
@Entity(name = "User")
public class Member extends BaseTimeEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true)
    private String idToken;

    @Column(length = 300)
    private String password;

    @Column(length = 10)
    private String nickName;

    @Column(length = 15)
    private String phoneNumber;

    @OneToOne
    @JoinColumn
    Image profileImage;

    @ElementCollection(fetch = FetchType.EAGER) //LAZY -> 오류
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Participation> participationList;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Remittance> remittanceList;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ranking> rankingEntityList;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private RecentRemittance recentRemittance;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Notification> sendNotificationList;

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Notification> receiveNotificationList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
                .stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.idToken;
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
}


