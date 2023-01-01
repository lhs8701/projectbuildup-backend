package projectbuildup.saver.domain.remittance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.challenge.entity.Challenge;
import projectbuildup.saver.domain.dto.req.RemitRequestDto;
import projectbuildup.saver.domain.recentremittance.service.RecentRemittanceService;
import projectbuildup.saver.domain.remittance.entity.Remittance;
import projectbuildup.saver.domain.remittance.repository.RemittanceJpaRepository;
import projectbuildup.saver.domain.user.entity.User;

@Service
@RequiredArgsConstructor
public class RemittanceService {

    private final RecentRemittanceService recentRemittanceService;
    private final RemittanceJpaRepository remittanceJpaRepository;

    public void remit(RemitRequestDto remitRequestDto) {
        Challenge challenge = Challenge.builder()
                .id(remitRequestDto.getChallengeId())
                .build();

        User userEntity = User.builder()
                .id(remitRequestDto.getUserId())
                .build();

        Remittance remittance = Remittance.builder()
                .challenge(challenge)
                .user(userEntity)
                .amount(remitRequestDto.getAmount())
                .build();

        recentRemittanceService.updateRecentInformation(userEntity, remittance);
        remittanceJpaRepository.save(remittance);
    }
}
