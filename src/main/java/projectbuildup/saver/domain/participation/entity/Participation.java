package projectbuildup.saver.domain.participation.entity;

import lombok.*;
import org.junit.jupiter.params.ParameterizedTest;
import projectbuildup.saver.domain.challenge.entity.Challenge;
import projectbuildup.saver.domain.user.entity.User;
import projectbuildup.saver.global.common.BaseTimeEntity;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
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

    @Builder
    public Participation(User user, Challenge challenge){
        this.user = user;
        this.challenge = challenge;
    }
}
