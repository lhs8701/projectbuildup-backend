package projectbuildup.saver.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.challenge.entity.ChallengeEntity;
import projectbuildup.saver.domain.challenge.repository.ChallengeRepository;
import projectbuildup.saver.domain.challenge.service.interfaces.ChallengeService;
import projectbuildup.saver.domain.dto.res.ViewChallengeResDto;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeRepository challengeRepository;
    @Override
    public ViewChallengeResDto viewChallenge(Long challengeId) {
        /*
        Optional<ChallengeEntity> optionalChallenge =  challengeRepository.findById(challengeId);
        
        //Exception Custom 할 것
        ChallengeEntity challenge = optionalChallenge.orElseThrow();

        Long participants = Long.valueOf(challenge.getUserEntityList().size());
        
        //Dto 생성
        ViewChallengeResDto viewChallengeResDto = ViewChallengeResDto.builder()
                .mainTitle(challenge.getMainTitle())
                .subTitle(challenge.getSubTitle())
                .savingAmount(challenge.getSavingAmount())
                .participants(participants)
                .endDate(challenge.getEndDate())
                .content(challenge.getContent())
                .build();

        return viewChallengeResDto;
         */
        return null;
    }

}
