package projectbuildup.saver.domain.recentsaving.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.saver.domain.saving.entity.SavingEntity;
import projectbuildup.saver.domain.user.entity.UserEntity;
import projectbuildup.saver.global.common.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecentSaving extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int totalCount;

    private long totalAmount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserEntity user;

    public void update(SavingEntity saving){
        this.totalAmount += saving.getAmount();
        this.totalCount++;
    }
}
