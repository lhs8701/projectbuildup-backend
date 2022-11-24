package projectbuildup.saver.domain.saving.entity;

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
@Entity(name = "Saving")
public class SavingEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //절약 금액
    private Long amount;

    //절약 날짜
    private String savingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private ChallengeEntity challenge;

}
