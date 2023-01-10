package projectbuildup.saver.domain.remittance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.challenge.entity.Challenge;
import projectbuildup.saver.domain.remittance.entity.Remittance;
import projectbuildup.saver.domain.remittance.repository.RemittanceJpaRepository;
import projectbuildup.saver.domain.user.entity.Member;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RemittanceFindService {
    private final RemittanceJpaRepository remittanceJpaRepository;

    public List<Remittance> findAllByChallengeAndUser(Challenge challenge, Member member) {
        return remittanceJpaRepository.findAllByChallengeAndMember(challenge, member);
    }
}
