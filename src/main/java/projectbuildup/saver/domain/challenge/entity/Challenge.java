package projectbuildup.saver.domain.challenge.entity;

import lombok.*;
import projectbuildup.saver.domain.dto.req.CreateChallengeRequestDto;
import projectbuildup.saver.domain.participation.entity.Participation;
import projectbuildup.saver.domain.dto.req.UpdateChallengeReqDto;
import projectbuildup.saver.domain.ranking.entity.Ranking;
import projectbuildup.saver.domain.remittance.entity.Remittance;
import projectbuildup.saver.global.common.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Challenge")
@RequiredArgsConstructor
@Builder
public class Challenge extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @NonNull
    private LocalDate startDate;

    @NonNull
    private LocalDate endDate;

    @OneToMany(mappedBy = "challenge", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Remittance> remittanceList;

    @OneToMany(mappedBy = "challenge", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ranking> rankingEntityList;

    @OneToMany(mappedBy = "challenge", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Participation> participationList;

    public void update(UpdateChallengeReqDto updated, LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.mainTitle = updated.getMainTitle();
        this.subTitle = updated.getSubTitle();
        this.content = updated.getContent();
        this.savingAmount = updated.getSavingAmount();
    }
}
