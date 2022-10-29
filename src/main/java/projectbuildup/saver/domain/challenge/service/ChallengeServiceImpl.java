package projectbuildup.saver.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.challenge.entity.ChallengeEntity;
import projectbuildup.saver.domain.challenge.repository.ChallengeRepository;
import projectbuildup.saver.domain.challenge.service.interfaces.ChallengeService;
import projectbuildup.saver.domain.dto.req.CreateChallengeReqDto;
import projectbuildup.saver.domain.dto.res.ViewChallengeResDto;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeRepository challengeRepository;

    @Override
    public ViewChallengeResDto viewChallenge(Long challengeId) {

        return null;
    }

    @Override
    public void createChallenge(CreateChallengeReqDto challengeReqDto) {
        ChallengeEntity challengeEntity = ChallengeEntity.builder()
                .startDate(challengeReqDto.getStartDate())
                .endDate(challengeReqDto.getEndDate())
                .mainTitle(challengeReqDto.getMainTitle())
                .subTitle(challengeReqDto.getSubTitle())
                .content(challengeReqDto.getContent())
                .savingAmount(challengeReqDto.getSavingAmount())
                .build();

        challengeRepository.save(challengeEntity);
    }

}
