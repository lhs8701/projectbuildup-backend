package projectbuildup.saver.domain.participation.entity;

import lombok.*;
import projectbuildup.saver.domain.challenge.entity.Challenge;
import projectbuildup.saver.domain.user.entity.Member;
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
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Challenge challenge;

    @Builder
    public Participation(Member member, Challenge challenge){
        this.member = member;
        this.challenge = challenge;
    }
}
