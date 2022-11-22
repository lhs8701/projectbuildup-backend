package projectbuildup.saver.domain.saving.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.challenge.entity.ChallengeEntity;
import projectbuildup.saver.domain.dto.req.SaveSavingReqDto;
import projectbuildup.saver.domain.saving.entity.SavingEntity;
import projectbuildup.saver.domain.saving.repository.SavingRepository;
import projectbuildup.saver.domain.user.entity.UserEntity;

@Service
@RequiredArgsConstructor
public class SavingServiceImpl implements SavingService{

    private final SavingRepository savingRepository;

    @Override
    public void saveSaving(SaveSavingReqDto saveSavingReqDto) {
        ChallengeEntity challengeEntity = ChallengeEntity.builder()
                .id(saveSavingReqDto.getChallengeId())
                .build();

        UserEntity userEntity = UserEntity.builder()
                .id(saveSavingReqDto.getUserId())
                .build();

        SavingEntity savingEntity = SavingEntity.builder()
                .challenge(challengeEntity)
                .user(userEntity)
                .amount(saveSavingReqDto.getAmount())
                .build();

        savingRepository.save(savingEntity);
    }
}
