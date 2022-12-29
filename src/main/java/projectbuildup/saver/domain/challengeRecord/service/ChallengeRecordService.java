package projectbuildup.saver.domain.challengeRecord.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.challenge.entity.Challenge;
import projectbuildup.saver.domain.dto.req.SaveSavingReqDto;
import projectbuildup.saver.domain.recentsaving.service.RecentSavingService;
import projectbuildup.saver.domain.challengeRecord.entity.ChallengeRecordEntity;
import projectbuildup.saver.domain.challengeRecord.repository.ChallengeRecordRepository;
import projectbuildup.saver.domain.user.entity.User;

@Service
@RequiredArgsConstructor
public class ChallengeRecordService {

    private final RecentSavingService recentSavingService;
    private final ChallengeRecordRepository challengeRecordRepository;

    public void saveSaving(SaveSavingReqDto saveSavingReqDto) {
        Challenge challenge = Challenge.builder()
                .id(saveSavingReqDto.getChallengeId())
                .build();

        User userEntity = User.builder()
                .id(saveSavingReqDto.getUserId())
                .build();

        ChallengeRecordEntity challengeRecordEntity = ChallengeRecordEntity.builder()
                .challenge(challenge)
                .user(userEntity)
                .amount(saveSavingReqDto.getAmount())
                .build();

        recentSavingService.updateRecentSaving(userEntity, challengeRecordEntity);
        challengeRecordRepository.save(challengeRecordEntity);
    }
}
