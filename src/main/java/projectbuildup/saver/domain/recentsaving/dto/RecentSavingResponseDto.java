package projectbuildup.saver.domain.recentsaving.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.saver.domain.recentsaving.entity.RecentSaving;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class RecentSavingResponseDto {
    int totalCount;
    int totalAmount;
    LocalDateTime recentSavingDate;

    public RecentSavingResponseDto(RecentSaving recentSaving){
        if (recentSaving == null){

        }
        this.totalCount = recentSaving.getTotalCount();
        this.totalAmount = recentSaving.getTotalCount();
        this.recentSavingDate = recentSaving.getModifiedDate();
    }
}
