package projectbuildup.saver.domain.recentremittance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.saver.domain.remittance.entity.Remittance;
import projectbuildup.saver.domain.user.entity.Member;
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
    private Member member;

    public void update(Remittance saving){
        this.totalAmount += saving.getAmount();
        this.totalCount++;
    }
}
