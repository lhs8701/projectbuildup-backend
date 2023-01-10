package projectbuildup.saver.domain.recentremittance.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.saver.domain.recentremittance.entity.RecentRemittance;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RecentRemittanceResponseDto {
    int totalCount;
    int totalAmount;
    LocalDateTime recentSavingDate;

    public RecentRemittanceResponseDto(RecentRemittance recentRemittance){
        if (recentRemittance == null){

        }
        this.totalCount = recentRemittance.getTotalCount();
        this.totalAmount = recentRemittance.getTotalCount();
        this.recentSavingDate = recentRemittance.getModifiedTime();
    }
}
