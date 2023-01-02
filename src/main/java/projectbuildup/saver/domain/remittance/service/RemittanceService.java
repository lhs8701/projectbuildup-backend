package projectbuildup.saver.domain.remittance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.challenge.entity.Challenge;
import projectbuildup.saver.domain.challenge.service.ChallengeFindService;
import projectbuildup.saver.domain.dto.req.RemitRequestDto;
import projectbuildup.saver.domain.recentremittance.service.RecentRemittanceService;
import projectbuildup.saver.domain.remittance.entity.Remittance;
import projectbuildup.saver.domain.remittance.repository.RemittanceJpaRepository;
import projectbuildup.saver.domain.user.entity.User;
import projectbuildup.saver.domain.user.service.UserFindService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RemittanceService {
    private final ChallengeFindService challengeFindService;
    private final UserFindService userFindService;
    private final RecentRemittanceService recentRemittanceService;
    private final RemittanceJpaRepository remittanceJpaRepository;
    private final RemittanceFindService remittanceFindService;

    public void remit(RemitRequestDto remitRequestDto) {
        Challenge challenge = challengeFindService.findById(remitRequestDto.getChallengeId());
        User user = userFindService.findById(remitRequestDto.getUserId());
        Remittance remittance = Remittance.builder()
                .challenge(challenge)
                .user(user)
                .amount(remitRequestDto.getAmount())
                .build();

        recentRemittanceService.updateRecentInformation(user, remittance);
        remittanceJpaRepository.save(remittance);
    }

    public Long calculateSum(User user, Challenge challenge){
        List<Remittance> remittanceList = remittanceFindService.findAllByChallengeAndUser(challenge, user);
        Long sum = 0L;
        for (Remittance saving : remittanceList) {
            sum += saving.getAmount();
        }
        return sum;
    }
}
