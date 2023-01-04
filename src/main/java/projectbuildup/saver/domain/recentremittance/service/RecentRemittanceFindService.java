package projectbuildup.saver.domain.recentremittance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.recentremittance.entity.RecentRemittance;
import projectbuildup.saver.domain.recentremittance.error.exception.CRecentRemittanceNotFoundException;
import projectbuildup.saver.domain.recentremittance.repository.RecentRemittanceJpaRepository;
import projectbuildup.saver.domain.user.entity.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecentRemittanceFindService {

    private final RecentRemittanceJpaRepository recentRemittanceJpaRepository;

    public RecentRemittance findByUserOrGetNull(User user){
        return recentRemittanceJpaRepository.findByUser(user).orElse(null);
    }
}
