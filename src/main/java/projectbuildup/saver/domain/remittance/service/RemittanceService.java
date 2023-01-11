package projectbuildup.saver.domain.remittance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.challenge.entity.Challenge;
import projectbuildup.saver.domain.challenge.service.ChallengeFindService;
import projectbuildup.saver.domain.dto.req.RemitRequestDto;
import projectbuildup.saver.domain.recentremittance.service.RecentRemittanceService;
import projectbuildup.saver.domain.remittance.entity.Remittance;
import projectbuildup.saver.domain.remittance.repository.RemittanceJpaRepository;
import projectbuildup.saver.domain.user.entity.Member;
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
        Member member = userFindService.findById(remitRequestDto.getUserId());
        Remittance remittance = Remittance.builder()
                .challenge(challenge)
                .member(member)
                .amount(remitRequestDto.getAmount())
                .build();

        recentRemittanceService.updateRecentInformation(member, remittance);
        remittanceJpaRepository.save(remittance);
    }

    public Long calculateSum(Member member, Challenge challenge){
        List<Remittance> remittanceList = remittanceFindService.findAllByChallengeAndUser(challenge, member);
        Long sum = 0L;
        for (Remittance saving : remittanceList) {
            sum += saving.getAmount();
        }
        return sum;
    }
}
