package projectbuildup.saver.domain.participation.entity;

import lombok.*;
import projectbuildup.saver.domain.challenge.entity.Challenge;
import projectbuildup.saver.domain.user.entity.User;
import projectbuildup.saver.global.common.BaseTimeEntity;

import javax.persistence.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Participation extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Challenge challenge;

    public void joinChallenge(User user, Challenge challenge) {
        this.user = user;
        this.challenge = challenge;
    }
}
