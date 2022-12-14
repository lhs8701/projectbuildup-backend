package projectbuildup.saver.domain.user.entity;

import lombok.*;
import projectbuildup.saver.domain.alarm.entity.AlarmEntity;
import projectbuildup.saver.domain.challengeLog.entity.ChallengeLogEntity;
import projectbuildup.saver.domain.ranking.entity.RankingEntity;
import projectbuildup.saver.domain.saving.entity.SavingEntity;
import projectbuildup.saver.global.entity.BaseTimeEntity;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="User")
public class UserEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true)
    private String loginId;

    @Column(length = 10)
    private String password;

    @Column(length = 10)
    private String nickName;

    @Column(length = 15)
    private String phoneNumber;

    //이미지 처리에 대해서 추후 얘기해 봐야 할 듯
    @Column(length = 100)
    private String profileImage;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChallengeLogEntity> challengeLogEntityList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SavingEntity> savingEntityList;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RankingEntity> rankingEntityList;

    @OneToMany(mappedBy = "sender", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AlarmEntity> sendAlarmEntityList;

    @OneToMany(mappedBy = "receiver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<AlarmEntity> receiveAlarmEntityList;

}
