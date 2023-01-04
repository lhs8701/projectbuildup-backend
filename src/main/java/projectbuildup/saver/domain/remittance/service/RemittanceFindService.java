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
public class RemittanceFindService {
    private final RemittanceJpaRepository remittanceJpaRepository;

    public List<Remittance> findAllByChallengeAndUser(Challenge challenge, User user) {
        return remittanceJpaRepository.findAllByChallengeAndUser(challenge, user);
    }
}
