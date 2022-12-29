package projectbuildup.saver.domain.challengeLog.entity;

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
@Entity(name = "ChallengeLog")
public class ChallengeLog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="challenge_id")
    private Challenge challenge;

    public void joinChallenge(User user, Challenge challenge) {
        this.user = user;
        this.challenge = challenge;
    }
}
