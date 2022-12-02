package projectbuildup.saver.domain.challenge.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.saver.domain.ranking.entity.RankingEntity;
import projectbuildup.saver.domain.saving.entity.ChallengeRecordEntity;
import projectbuildup.saver.domain.user.entity.UserEntity;
import projectbuildup.saver.global.entity.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Challenge")
public class ChallengeEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 40)
    private LocalDateTime startDate;

    @Column(length = 40)
    private LocalDateTime endDate;

    @Column(length = 30)
    private String mainTitle;

    @Column(length = 30)
    private String subTitle;

    @Column(length = 200)
    private String content;

    @Column(length = 30)
    private Long savingAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "challenge", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChallengeRecordEntity> challengeRecordEntityList;

    @OneToMany(mappedBy = "challenge", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RankingEntity> rankingEntityList;

}
