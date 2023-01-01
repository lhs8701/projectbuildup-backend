package projectbuildup.saver.domain.challengeRecord.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.challenge.entity.Challenge;
import projectbuildup.saver.domain.dto.req.SaveSavingReqDto;
import projectbuildup.saver.domain.recentremittance.service.RecentRemittanceService;
import projectbuildup.saver.domain.challengeRecord.entity.Remittance;
import projectbuildup.saver.domain.challengeRecord.repository.RemittanceJpaRepository;
import projectbuildup.saver.domain.user.entity.User;

@Service
@RequiredArgsConstructor
public class RemittanceService {

    private final RecentRemittanceService recentRemittanceService;
    private final RemittanceJpaRepository remittanceJpaRepository;

    public void saveSaving(SaveSavingReqDto saveSavingReqDto) {
        Challenge challenge = Challenge.builder()
                .id(saveSavingReqDto.getChallengeId())
                .build();

        User userEntity = User.builder()
                .id(saveSavingReqDto.getUserId())
                .build();

        Remittance remittance = Remittance.builder()
                .challenge(challenge)
                .user(userEntity)
                .amount(saveSavingReqDto.getAmount())
                .build();

        recentRemittanceService.updateRecentSaving(userEntity, remittance);
        remittanceJpaRepository.save(remittance);
    }
}
