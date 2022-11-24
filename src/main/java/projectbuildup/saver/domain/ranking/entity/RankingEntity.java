package projectbuildup.saver.domain.ranking.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.saver.domain.challenge.entity.ChallengeEntity;
import projectbuildup.saver.domain.user.entity.UserEntity;
import projectbuildup.saver.global.common.BaseTimeEntity;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Ranking")
public class RankingEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    private Long recentRanking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "challenge_id")
    private ChallengeEntity challenge;

}
