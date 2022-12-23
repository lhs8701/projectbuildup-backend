package projectbuildup.saver.domain.recentsaving.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.recentsaving.dto.RecentSavingResponseDto;
import projectbuildup.saver.domain.recentsaving.entity.RecentSaving;
import projectbuildup.saver.domain.recentsaving.error.exception.CRecentSavingNotFoundException;
import projectbuildup.saver.domain.recentsaving.repository.RecentSavingRepository;
import projectbuildup.saver.domain.saving.entity.SavingEntity;
import projectbuildup.saver.domain.user.dto.UserIdRequestParam;
import projectbuildup.saver.domain.user.entity.UserEntity;
import projectbuildup.saver.domain.user.error.exception.CUserNotFoundException;
import projectbuildup.saver.domain.user.repository.UserJpaRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RecentSavingService {

    private final UserJpaRepository userJpaRepository;
    private final RecentSavingRepository recentSavingRepository;

    /**
     * 사용자의 최근 절약 정보를 반환한다.
     * @param idToken 아이디 토큰
     * @return 총 절약 횟수, 총 절약 금액, 최근 절약일
     */
    public RecentSavingResponseDto getRecentSaving(String idToken){
        UserEntity user = userJpaRepository.findByIdToken(idToken).orElseThrow(CUserNotFoundException::new);
        RecentSaving recentSaving = recentSavingRepository.findByUserEntity(user).orElse(null);
        if (recentSaving == null){
            return null;
        }
        return new RecentSavingResponseDto(recentSaving);
    }

    /**
     * 송금 정보를 바탕으로 사용자의 최근 절약 정보를 갱신한다.
     * @param idToken 아이디 토큰
     * @param saving 송금 정보
     */
    public void updateRecentSaving(String idToken, SavingEntity saving){
        UserEntity user = userJpaRepository.findByIdToken(idToken).orElseThrow(CUserNotFoundException::new);
        RecentSaving recentSaving = recentSavingRepository.findByUserEntity(user).orElseThrow(CRecentSavingNotFoundException::new);
        recentSaving.update(saving);
    }
}
