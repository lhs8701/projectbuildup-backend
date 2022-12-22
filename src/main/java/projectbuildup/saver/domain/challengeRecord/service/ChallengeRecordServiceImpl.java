package projectbuildup.saver.domain.challengeRecord.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.challenge.entity.ChallengeEntity;
import projectbuildup.saver.domain.dto.req.SaveSavingReqDto;
import projectbuildup.saver.domain.challengeRecord.entity.ChallengeRecordEntity;
import projectbuildup.saver.domain.challengeRecord.repository.ChallengeRecordRepository;
import projectbuildup.saver.domain.user.entity.UserEntity;

@Service
@RequiredArgsConstructor
public class ChallengeRecordServiceImpl implements ChallengeRecordService {

    private final ChallengeRecordRepository challengeRecordRepository;

    @Override
    public void saveSaving(SaveSavingReqDto saveSavingReqDto) {
        ChallengeEntity challengeEntity = ChallengeEntity.builder()
                .id(saveSavingReqDto.getChallengeId())
                .build();

        UserEntity userEntity = UserEntity.builder()
                .id(saveSavingReqDto.getUserId())
                .build();

        ChallengeRecordEntity challengeRecordEntity = ChallengeRecordEntity.builder()
                .challenge(challengeEntity)
                .user(userEntity)
                .amount(saveSavingReqDto.getAmount())
                .build();

        challengeRecordRepository.save(challengeRecordEntity);
    }
}
