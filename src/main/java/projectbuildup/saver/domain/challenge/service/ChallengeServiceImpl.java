package projectbuildup.saver.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.challenge.entity.ChallengeEntity;
import projectbuildup.saver.domain.challenge.repository.ChallengeRepository;
import projectbuildup.saver.domain.challenge.service.interfaces.ChallengeService;
import projectbuildup.saver.domain.challengeLog.entity.ChallengeLogEntity;
import projectbuildup.saver.domain.challengeLog.repository.ChallengeLogRepository;
import projectbuildup.saver.domain.dto.req.CreateChallengeReqDto;
import projectbuildup.saver.domain.dto.res.ParticipantResDto;
import projectbuildup.saver.domain.dto.res.ViewChallengeResDto;
import projectbuildup.saver.domain.saving.entity.SavingEntity;
import projectbuildup.saver.domain.saving.repository.SavingRepository;
import projectbuildup.saver.domain.user.entity.UserEntity;
import projectbuildup.saver.domain.user.repository.UserRepository;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChallengeServiceImpl implements ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeLogRepository challengeLogRepository;
    private final UserRepository userRepository;
    private final SavingRepository savingRepository;

    @Override
    public ViewChallengeResDto viewChallenge(Long challengeId) {
        List<ChallengeLogEntity> challengeLogEntityList = challengeLogRepository.findByChallenge_Id(challengeId);
        List<ParticipantResDto> participantResDtoList = new ArrayList<>();

        for(ChallengeLogEntity c : challengeLogEntityList) {
            Optional<UserEntity> optionalUserEntity = userRepository.findById(c.getUser().getId());
            UserEntity userEntity = optionalUserEntity.orElseThrow();

            List<SavingEntity> savingEntityList = savingRepository.findByChallenge_IAndUser_Id(challengeId, userEntity.getId());
            Long savingAmount = 0L;


            for(SavingEntity saving : savingEntityList) {
                savingAmount += saving.getAmount();
            }

            ParticipantResDto participantResDto = ParticipantResDto.builder()
                    .nickName(userEntity.getNickName())
                    .savingAmount(savingAmount)
                    .build();

            participantResDtoList.add(participantResDto);
        }

        //정렬 알고리즘 구현
        Comparator<ParticipantResDto> comparator = new Comparator<ParticipantResDto>() {
            @Override
            public int compare(ParticipantResDto p1, ParticipantResDto p2) {
                Long difference = (p1.getSavingAmount()-p2.getSavingAmount());

                return difference.intValue();
            }
        };

        Collections.sort(participantResDtoList, comparator);
        
        //결과들 ViewChallengeResDto에 집어넣기
        ViewChallengeResDto viewChallengeResDto = ViewChallengeResDto.builder()
                .participantResDtoList(participantResDtoList)
                .participantCnt(Long.valueOf(participantResDtoList.size()))
                .build();

        return viewChallengeResDto;
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
