package projectbuildup.saver.domain.recentremittance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.recentremittance.entity.RecentRemittance;
import projectbuildup.saver.domain.recentremittance.repository.RecentRemittanceJpaRepository;
import projectbuildup.saver.domain.user.entity.Member;

@Service
@RequiredArgsConstructor
public class RecentRemittanceFindService {

    private final RecentRemittanceJpaRepository recentRemittanceJpaRepository;

    public RecentRemittance findByUserOrGetNull(Member member){
        return recentRemittanceJpaRepository.findByUser(member).orElse(null);
    }
}
