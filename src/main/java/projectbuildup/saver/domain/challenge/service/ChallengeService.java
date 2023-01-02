package projectbuildup.saver.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.challenge.entity.Challenge;
import projectbuildup.saver.domain.challenge.error.exception.CChallengeNotFoundException;
import projectbuildup.saver.domain.challenge.error.exception.CUserAlreadyJoinedException;
import projectbuildup.saver.domain.challenge.repository.ChallengeJpaRepository;
import projectbuildup.saver.domain.participation.entity.Participation;
import projectbuildup.saver.domain.participation.repository.ParticipationJpaRepository;
import projectbuildup.saver.domain.dto.req.CreateChallengeReqDto;
import projectbuildup.saver.domain.dto.req.UpdateChallengeReqDto;
import projectbuildup.saver.domain.dto.res.GetChallengeListResDto;
import projectbuildup.saver.domain.dto.res.GetChallengeResponseDto;
import projectbuildup.saver.domain.dto.res.ParticipantResDto;
import projectbuildup.saver.domain.dto.res.GetChallengeParticipantsResDto;
import projectbuildup.saver.domain.remittance.entity.Remittance;
import projectbuildup.saver.domain.remittance.repository.RemittanceJpaRepository;
import projectbuildup.saver.domain.user.entity.User;
import projectbuildup.saver.domain.user.error.exception.CUserNotFoundException;
import projectbuildup.saver.domain.user.repository.UserJpaRepository;
import projectbuildup.saver.global.util.StringDateConverter;

import java.time.LocalDate;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeJpaRepository challengeJpaRepository;
    private final ChallengeFindService challengeFindService;

    private final ParticipationJpaRepository participationJpaRepository;
    private final UserJpaRepository userJpaRepository;
    private final RemittanceJpaRepository remittanceJpaRepository;

    private final StringDateConverter stringDateConverter;

    public GetChallengeParticipantsResDto getChallengeParticipants(Long challengeId) {
        // 챌린지에 참여한 사람 확인
        List<Participation> participationList = participationJpaRepository.findByChallenge_Id(challengeId);

        List<ParticipantResDto> participantResDtoList = new ArrayList<>();

        // 참여한 모든 유저에 대해 For Loop
        for (Participation c : participationList) {
            // id를 통해 유저 Entity를 가져옴
            // 유저가 없을시 Exception throw -> 404 NOT FOUND
            User userEntity = userJpaRepository.findById(c.getUser().getId()).orElseThrow(CUserNotFoundException::new);


            // 본 유저가 본 챌린지에 모았던 기록들을 모두 가져 온 후 총액을 계산함.
            List<Remittance> remittanceList = remittanceJpaRepository.findByChallengeIdAndUserId(challengeId, userEntity.getId());
            Long savingAmount = 0L;
            for (Remittance saving : remittanceList) {
                savingAmount += saving.getAmount();
            }

            // 참여자 추가.
            ParticipantResDto participantResDto = ParticipantResDto.builder()
                    .loginId(userEntity.getIdToken())
                    .nickName(userEntity.getNickName())
                    .savingAmount(savingAmount)
                    .build();
            participantResDtoList.add(participantResDto);
        }

        //정렬 알고리즘 구현
        Comparator<ParticipantResDto> comparator = (p1, p2) -> {
            long difference = (p1.getSavingAmount() - p2.getSavingAmount());
            return (int) difference;
        };

        participantResDtoList.sort(comparator);

        // 결과들 ViewChallengeResDto에 집어넣고
        // 리스트 반환
        return GetChallengeParticipantsResDto.builder()
                .participantResDtoList(participantResDtoList)
                .participantCnt((long) participantResDtoList.size())
                .build();
    }

    public void createChallenge(CreateChallengeReqDto challengeReqDto) {
        LocalDate startDate = stringDateConverter.convertToLocalDate(challengeReqDto.getStartDate());
        LocalDate endDate = stringDateConverter.convertToLocalDate(challengeReqDto.getEndDate());
        Challenge challenge = Challenge.builder()
                .challengeReqDto(challengeReqDto)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        challengeJpaRepository.save(challenge);
    }

//    public GetChallengeListResDto getAvailableChallenges(Long sortType, Boolean ascending, String loginId) {
//
//        // 모든 챌린지 가져옴
//        List<Challenge> challenges = challengeFindService.findAll();
//
//        List<Challenge> selectedChallenges = new ArrayList<>(
//                challenges
//                        .stream()
//                        .filter((challenge) -> {
//                            // 챌린지 중 자신의 loginId가 있는 챌린지는 제외함.
//                            for (Participation c : challenge.getParticipationList()) {
//                                User user = userJpaRepository.findById(c.getUser().getId()).orElseThrow(CUserNotFoundException::new);
//                                return !loginId.equals(user.getIdToken());
//                            }
//                            return true;
//                        })
//                        .collect(Collectors.toList())
//        );
//
//
//        // sort 방식에 따라 정렬함.
//        if (sortType == 1) {
//            selectedChallenges.sort((a, b) -> {
//                return ascending ? a.getParticipationList().size() - b.getParticipationList().size() : b.getParticipationList().size() - a.getParticipationList().size();
//            });
//        } else if (sortType == 2) {
//            selectedChallenges.sort((a, b) -> {
//                return ascending ? (int) (a.getSavingAmount() - b.getSavingAmount()) : (int) (b.getSavingAmount() - a.getSavingAmount());
//            });
//        }
////        else if (sortType == 3) {
////            selectedChallenges.sort((a, b) -> {
////                LocalDate aDate = LocalDate.parse(a.getEndDate(), DateTimeFormatter.ofPattern("yyyy.MM.dd"));
////                LocalDate bDate = LocalDate.parse(b.getEndDate(), DateTimeFormatter.ofPattern("yyyy.MM.dd"));
////                return ascending ? aDate.compareTo(bDate) : bDate.compareTo(aDate);
////            });
////        }
//        else {
//            return null;
//        }
//
//        List<GetChallengeResDto> challengeList = (List<GetChallengeResDto>) selectedChallenges
//                .stream()
//                .map((challenge) -> {
//                    return new GetChallengeResDto(
//                            challenge.getId(),
//                            challenge.getStartDate(),
//                            challenge.getEndDate(),
//                            challenge.getMainTitle(),
//                            challenge.getSubTitle(),
//                            challenge.getContent(),
//                            challenge.getSavingAmount(),
//                            (long) challenge.getParticipationList().size()
//                    );
//                })
//                .collect(Collectors.toList());
//
//        // Dto로 변환 후 리턴.
//        return GetChallengeListResDto.builder()
//                .challengCnt((long) challengeList.size())
//                .challengeList(challengeList)
//                .build();
//    }

    public GetChallengeResponseDto getChallenge(Long challengeId) {
        Challenge challenge = challengeFindService.findById(challengeId);
        return new GetChallengeResponseDto(challenge);
    }

//    public GetChallengeListResDto getMyChallenges(String loginId) {
//        // 전부 찾아서 loginId가 같은 user가 있는 챌린지만 추려낸 후 리턴.
//        List<Challenge> challenges = challengeFindService.findAll();
//        List<Challenge> userChallenges = challenges
//                .stream()
//                .filter((challenge) -> {
//                    List<Participation> participations = challenge.getParticipationList();
//                    for (Participation c : participations) {
//                        User user = userJpaRepository.findById(c.getUser().getId()).orElseThrow(CUserNotFoundException::new);
//                        if (loginId.equals(user.getIdToken())) {
//                            return true;
//                        }
//                    }
//                    return false;
//                })
//                .collect(Collectors.toList());
//
//        List<GetChallengeResponseDto> challengeList = (List<GetChallengeResponseDto>) userChallenges
//                .stream()
//                .map((challenge) -> {
//                    return new GetChallengeResponseDto(
//                            challenge.getId(),
//                            challenge.getStartDate(),
//                            challenge.getEndDate(),
//                            challenge.getMainTitle(),
//                            challenge.getSubTitle(),
//                            challenge.getContent(),
//                            challenge.getSavingAmount(),
//                            (long) challenge.getParticipationList().size()
//                    );
//                })
//                .collect(Collectors.toList());
//
//        // Dto로 변환 후 리턴.
//        return GetChallengeListResDto.builder()
//                .challengCnt((long) challengeList.size())
//                .challengeList(challengeList)
//                .build();
//    }

    public void joinChallenge(String idToken, Long challengeId) {
        Challenge challenge = challengeFindService.findById(challengeId);
        User user = userJpaRepository.findByIdToken(idToken).orElseThrow(CUserNotFoundException::new);
        if (participationJpaRepository.findByChallengeAndUser(challenge, user).isPresent()) {
            throw new CUserAlreadyJoinedException();
        }
        Participation log = new Participation();
        log.joinChallenge(user, challenge);
        participationJpaRepository.save(log);
    }

    public void leftChallenge(String idToken, Long challengeId) {
        Challenge challenge = challengeFindService.findById(challengeId);
        User user = userJpaRepository.findByIdToken(idToken).orElseThrow(CUserAlreadyJoinedException::new);
        participationJpaRepository.deleteByChallengeAndUser(challenge, user);
    }

    public void updateChallenge(Long challengeId, UpdateChallengeReqDto updated) {
        LocalDate testDate = Year.of(2023).atMonth(1).atDay(2);
        Challenge challenge = challengeFindService.findById(challengeId);
        challenge.update(updated, testDate, testDate);
        challengeJpaRepository.save(challenge);
    }

    public void deleteChallenge(Long challengeId) {
        try {
            challengeJpaRepository.deleteById(challengeId);
        } catch (IllegalArgumentException e) {
            throw new CChallengeNotFoundException();
        }
    }
}
