package projectbuildup.saver.domain.challenge.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.saver.domain.challengeLog.entity.ChallengeLogEntity;
import projectbuildup.saver.domain.ranking.entity.RankingEntity;
import projectbuildup.saver.domain.saving.entity.SavingEntity;
import projectbuildup.saver.global.common.BaseTimeEntity;

import javax.persistence.*;
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

    /**
     * yyyy.MM.dd
     */
    @Column(length = 40)
    private String startDate;

    /**
     * yyyy.MM.dd
     */
    @Column(length = 40)
    private String endDate;

    @Column(length = 30)
    private String mainTitle;

    @Column(length = 30)
    private String subTitle;

    @Column(length = 200)
    private String content;

    @Column(length = 30)
    private Long savingAmount;

    @OneToMany(mappedBy = "challenge", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SavingEntity> savingEntityList;

    @OneToMany(mappedBy = "challenge", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RankingEntity> rankingEntityList;

    @OneToMany(mappedBy = "challenge", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChallengeLogEntity> challengeLogEntityList;

}
