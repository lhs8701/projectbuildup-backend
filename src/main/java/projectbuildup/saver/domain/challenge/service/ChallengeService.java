package projectbuildup.saver.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.challenge.entity.Challenge;
import projectbuildup.saver.domain.challenge.error.exception.CChallengeNotFoundException;
import projectbuildup.saver.domain.challenge.repository.ChallengeJpaRepository;
import projectbuildup.saver.domain.dto.req.CreateChallengeRequestDto;
import projectbuildup.saver.domain.dto.req.UpdateChallengeReqDto;
import projectbuildup.saver.domain.dto.res.ParticipantsResponseDto;
import projectbuildup.saver.domain.dto.res.ChallengeResponseDto;
import projectbuildup.saver.domain.dto.res.ParticipantDto;
import projectbuildup.saver.domain.participation.entity.Participation;
import projectbuildup.saver.domain.participation.repository.ParticipationJpaRepository;
import projectbuildup.saver.domain.participation.service.ParticipationFindService;
import projectbuildup.saver.domain.participation.service.ParticipationService;
import projectbuildup.saver.domain.remittance.service.RemittanceFindService;
import projectbuildup.saver.domain.remittance.service.RemittanceService;
import projectbuildup.saver.domain.user.entity.User;
import projectbuildup.saver.domain.user.service.UserFindService;
import projectbuildup.saver.global.util.StringDateConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeJpaRepository challengeJpaRepository;
    private final ChallengeFindService challengeFindService;

    private final ParticipationFindService participationFindService;
    private final ParticipationService participationService;
    private final UserFindService userFindService;
    private final RemittanceFindService remittanceFindService;
    private final RemittanceService remittanceService;
    private final StringDateConverter stringDateConverter;

    public ParticipantsResponseDto getChallengeParticipants(Long challengeId) {
        Challenge challenge = challengeFindService.findById(challengeId);
        List<Participation> participationList = participationFindService.findAllByChallenge(challenge);
        List<ParticipantDto> responseDtoList = new ArrayList<>();
        for (Participation c : participationList) {
            User user = userFindService.findById(c.getId());
            Long totalAmount = remittanceService.calculateSum(user, challenge);
            ParticipantDto participantDto = new ParticipantDto(user, totalAmount);
            responseDtoList.add(participantDto);
        }
        //정렬 알고리즘 구현
        Comparator<ParticipantDto> comparator = (p1, p2) -> {
            long difference = (p1.getTotalAmount() - p2.getTotalAmount());
            return (int) difference;
        };

        responseDtoList.sort(comparator);
        return new ParticipantsResponseDto(responseDtoList);
    }

    public void createChallenge(CreateChallengeRequestDto challengeReqDto) {
        LocalDate startDate = stringDateConverter.convertToLocalDate(challengeReqDto.getStartDate());
        LocalDate endDate = stringDateConverter.convertToLocalDate(challengeReqDto.getEndDate());
        Challenge challenge = Challenge.builder()
                .challengeReqDto(challengeReqDto)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        challengeJpaRepository.save(challenge);
    }

    public ChallengeResponseDto getChallenge(Long challengeId) {
        Challenge challenge = challengeFindService.findById(challengeId);
        return new ChallengeResponseDto(challenge);
    }


    public void joinChallenge(String idToken, Long challengeId) {
        Challenge challenge = challengeFindService.findById(challengeId);
        User user = userFindService.findByIdToken(idToken);
        participationService.validateParticipationExistence(challenge, user);
        participationService.makeParticipation(challenge, user);
    }

    public void giveUpChallenge(String idToken, Long challengeId) {
        Challenge challenge = challengeFindService.findById(challengeId);
        User user = userFindService.findByIdToken(idToken);
        participationService.deleteByChallengeAndUser(challenge, user);
    }

    public void updateChallenge(Long challengeId, UpdateChallengeReqDto updated) {
        LocalDate startDate = stringDateConverter.convertToLocalDate(updated.getStartDate());
        LocalDate endDate = stringDateConverter.convertToLocalDate(updated.getEndDate());
        Challenge challenge = challengeFindService.findById(challengeId);
        challenge.update(updated, startDate, endDate);
        challengeJpaRepository.save(challenge);
    }

    public void deleteChallenge(Long challengeId) {
        try {
            challengeJpaRepository.deleteById(challengeId);
        } catch (IllegalArgumentException e) {
            throw new CChallengeNotFoundException();
        }
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

}
