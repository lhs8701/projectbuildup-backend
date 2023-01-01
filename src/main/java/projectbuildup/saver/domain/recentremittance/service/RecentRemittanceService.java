package projectbuildup.saver.domain.recentremittance.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.remittance.entity.Remittance;
import projectbuildup.saver.domain.recentremittance.dto.RecentRemittanceResponseDto;
import projectbuildup.saver.domain.recentremittance.entity.RecentRemittance;
import projectbuildup.saver.domain.recentremittance.repository.RecentRemittanceJpaRepository;
import projectbuildup.saver.domain.user.entity.User;
import projectbuildup.saver.domain.user.error.exception.CUserNotFoundException;
import projectbuildup.saver.domain.user.repository.UserJpaRepository;

@Service
@RequiredArgsConstructor
public class RecentRemittanceService {

    private final UserJpaRepository userJpaRepository;
    private final RecentRemittanceJpaRepository recentRemittanceJpaRepository;

    private final RecentRemittanceFindService recentRemittanceFindService;

    /**
     * 사용자의 최근 절약 정보를 반환한다.
     *
     * @param idToken 아이디 토큰
     * @return 총 절약 횟수, 총 절약 금액, 최근 절약일
     */
    public RecentRemittanceResponseDto getRecentSaving(String idToken) {
        User user = userJpaRepository.findByIdToken(idToken).orElseThrow(CUserNotFoundException::new);
        RecentRemittance recentRemittance = recentRemittanceFindService.findByUserOrGetNull(user);

        if (recentRemittance == null) {
            return null;
        }
        return new RecentRemittanceResponseDto(recentRemittance);
    }

    /**
     * 송금 정보를 바탕으로 사용자의 최근 절약 정보를 갱신한다.
     *만약, 첫 송금인 경우, 최근 절약 정보를 새로 생성한다.
     * @param user   유저
     * @param saving 송금 정보
     */
    public void updateRecentSaving(User user, Remittance saving) {
        RecentRemittance recentRemittance = recentRemittanceFindService.findByUserOrGetNull(user);
        if (recentRemittance == null) {
            createRecentSaving(user, saving);
            return;
        }
        recentRemittance.update(saving);
    }

    /**
     * 사용자의 최근 절약 정보가 존재하지 않을 경우, 새로 생성한다.
     * @param user 유저
     * @param saving 송금 정보
     */
    private void createRecentSaving(User user, Remittance saving) {
        RecentRemittance recentInformation = RecentRemittance.builder()
                .totalAmount(saving.getAmount())
                .totalCount(1)
                .user(user)
                .build();
        recentRemittanceJpaRepository.save(recentInformation);
    }
}
