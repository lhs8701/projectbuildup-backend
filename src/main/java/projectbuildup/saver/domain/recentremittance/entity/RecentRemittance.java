package projectbuildup.saver.domain.recentremittance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.saver.domain.challengeRecord.entity.Remittance;
import projectbuildup.saver.domain.user.entity.User;
import projectbuildup.saver.global.common.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecentRemittance extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int totalCount;

    private long totalAmount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

    public void update(Remittance saving){
        this.totalAmount += saving.getAmount();
        this.totalCount++;
    }
}
