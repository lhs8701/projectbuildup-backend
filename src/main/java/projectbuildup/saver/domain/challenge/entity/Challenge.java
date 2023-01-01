package projectbuildup.saver.domain.challenge.entity;

import lombok.*;
import projectbuildup.saver.domain.participation.entity.Participation;
import projectbuildup.saver.domain.dto.req.UpdateChallengeReqDto;
import projectbuildup.saver.domain.ranking.entity.Ranking;
import projectbuildup.saver.domain.remittance.entity.Remittance;
import projectbuildup.saver.global.common.BaseTimeEntity;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Challenge")
@RequiredArgsConstructor
public class Challenge extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * yyyy.MM.dd
     */
    @NonNull
    @Column(length = 40)
    private String startDate;

    /**
     * yyyy.MM.dd
     */
    @NonNull
    @Column(length = 40)
    private String endDate;

    @NonNull
    @Column(length = 30)
    private String mainTitle;

    @NonNull
    @Column(length = 30)
    private String subTitle;

    @NonNull
    @Column(length = 200)
    private String content;

    @NonNull
    @Column(length = 30)
    private Long savingAmount;

    @OneToMany(mappedBy = "challenge", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Remittance> remittanceList;

    @OneToMany(mappedBy = "challenge", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ranking> rankingEntityList;

    @OneToMany(mappedBy = "challenge", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Participation> participationList;

    public void updateChallenge(UpdateChallengeReqDto updated) {
        this.startDate = updated.getStartDate();
        this.endDate = updated.getEndDate();
        this.mainTitle = updated.getMainTitle();
        this.subTitle = updated.getSubTitle();
        this.content = updated.getContent();
        this.savingAmount = updated.getSavingAmount();
    }

}
