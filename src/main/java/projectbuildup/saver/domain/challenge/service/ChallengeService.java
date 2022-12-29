package projectbuildup.saver.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.challenge.entity.Challenge;
import projectbuildup.saver.domain.challenge.error.exception.CChallengeNotFoundException;
import projectbuildup.saver.domain.challenge.error.exception.CUserAlreadyJoinedException;
import projectbuildup.saver.domain.challenge.repository.ChallengeRepository;
import projectbuildup.saver.domain.challengeLog.entity.ChallengeLog;
import projectbuildup.saver.domain.challengeLog.repository.ChallengeLogRepository;
import projectbuildup.saver.domain.dto.req.CreateChallengeReqDto;
import projectbuildup.saver.domain.dto.req.UpdateChallengeReqDto;
import projectbuildup.saver.domain.dto.res.GetChallengeListResDto;
import projectbuildup.saver.domain.dto.res.GetChallengeResDto;
import projectbuildup.saver.domain.dto.res.ParticipantResDto;
import projectbuildup.saver.domain.dto.res.GetChallengeParticipantsResDto;
import projectbuildup.saver.domain.challengeRecord.entity.ChallengeRecordEntity;
import projectbuildup.saver.domain.challengeRecord.repository.ChallengeRecordRepository;
import projectbuildup.saver.domain.user.entity.User;
import projectbuildup.saver.domain.user.error.exception.CUserNotFoundException;
import projectbuildup.saver.domain.user.repository.UserRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeLogRepository challengeLogRepository;
    private final UserRepository userRepository;
    private final ChallengeRecordRepository challengeRecordRepository;

    public GetChallengeParticipantsResDto getChallengeParticipants(Long challengeId) {
        // 챌린지에 참여한 사람 확인
        List<ChallengeLog> challengeLogList = challengeLogRepository.findByChallenge_Id(challengeId);

        List<ParticipantResDto> participantResDtoList = new ArrayList<>();

        // 참여한 모든 유저에 대해 For Loop
        for(ChallengeLog c : challengeLogList) {
            // id를 통해 유저 Entity를 가져옴
            // 유저가 없을시 Exception throw -> 404 NOT FOUND
            User userEntity = userRepository.findById(c.getUser().getId()).orElseThrow(CUserNotFoundException::new);


            // 본 유저가 본 챌린지에 모았던 기록들을 모두 가져 온 후 총액을 계산함.
            List<ChallengeRecordEntity> challengeRecordEntityList = challengeRecordRepository.findByChallengeIdAndUserId(challengeId, userEntity.getId());
            Long savingAmount = 0L;
            for(ChallengeRecordEntity saving : challengeRecordEntityList) {
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
            long difference = (p1.getSavingAmount()-p2.getSavingAmount());
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
        Challenge challenge = Challenge.builder()
                .startDate(challengeReqDto.getStartDate())
                .endDate(challengeReqDto.getEndDate())
                .mainTitle(challengeReqDto.getMainTitle())
                .subTitle(challengeReqDto.getSubTitle())
                .content(challengeReqDto.getContent())
                .savingAmount(challengeReqDto.getSavingAmount())
                .build();

        challengeRepository.save(challenge);
    }

    public GetChallengeListResDto getAvailableChallenges(Long sortType, Boolean ascending, String loginId) {

        // 모든 챌린지 가져옴
        List<Challenge> challenges = challengeRepository.findAll();

        List<Challenge> selectedChallenges = new ArrayList<>(
                challenges
                        .stream()
                        .filter((challenge) -> {
                    // 챌린지 중 자신의 loginId가 있는 챌린지는 제외함.
                            for(ChallengeLog c: challenge.getChallengeLogList()) {
                                User user = userRepository.findById(c.getUser().getId()).orElseThrow(CUserNotFoundException::new);
                                return !loginId.equals(user.getIdToken());
                            }
                            return true;
                        })
                        .collect(Collectors.toList())
                );


        // sort 방식에 따라 정렬함.
        if(sortType == 1) {
            selectedChallenges.sort((a, b) -> {
                return ascending ? a.getChallengeLogList().size() - b.getChallengeLogList().size() : b.getChallengeLogList().size() - a.getChallengeLogList().size();
            });
        } else if (sortType == 2) {
            selectedChallenges.sort((a, b) -> {
                return ascending ? (int)(a.getSavingAmount() - b.getSavingAmount()) : (int)(b.getSavingAmount() - a.getSavingAmount());
            });
        } else if (sortType == 3) {
            selectedChallenges.sort((a, b) -> {
                    LocalDate aDate = LocalDate.parse(a.getEndDate(), DateTimeFormatter.ofPattern("yyyy.MM.dd"));
                    LocalDate bDate = LocalDate.parse(b.getEndDate(), DateTimeFormatter.ofPattern("yyyy.MM.dd"));
                    return ascending ? aDate.compareTo(bDate) : bDate.compareTo(aDate);
            });
        } else {
            return null;
        }

        List<GetChallengeResDto> challengeList = (List<GetChallengeResDto>) selectedChallenges
                .stream()
                .map((challenge) -> {
                    return new GetChallengeResDto(
                            challenge.getId(),
                            challenge.getStartDate(),
                            challenge.getEndDate(),
                            challenge.getMainTitle(),
                            challenge.getSubTitle(),
                            challenge.getContent(),
                            challenge.getSavingAmount(),
                            (long) challenge.getChallengeLogList().size()
                    );
                })
                .collect(Collectors.toList());

        // Dto로 변환 후 리턴.
        return GetChallengeListResDto.builder()
                .challengCnt((long) challengeList.size())
                .challengeList(challengeList)
                .build();
    }

    public GetChallengeResDto getChallenge(Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(CChallengeNotFoundException::new);
        return new GetChallengeResDto(
                    challenge.getId(),
                    challenge.getStartDate(),
                    challenge.getEndDate(),
                    challenge.getMainTitle(),
                    challenge.getSubTitle(),
                    challenge.getContent(),
                    challenge.getSavingAmount(),
                    (long) challenge.getChallengeLogList().size()
                );
    }

    public GetChallengeListResDto getMyChallenges(String loginId) {
        // 전부 찾아서 loginId가 같은 user가 있는 챌린지만 추려낸 후 리턴.
        List<Challenge> challenges = challengeRepository.findAll();

        List<Challenge> userChallenges = challenges
                .stream()
                .filter((challenge) -> {
                    List<ChallengeLog> challengeLogs = challenge.getChallengeLogList();
                    for(ChallengeLog c: challengeLogs) {
                        User user = userRepository.findById(c.getUser().getId()).orElseThrow(CUserNotFoundException::new);
                        if (loginId.equals(user.getIdToken())) {
                            return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());

        List<GetChallengeResDto> challengeList = (List<GetChallengeResDto>) userChallenges
                .stream()
                .map((challenge) -> {
                    return new GetChallengeResDto(
                            challenge.getId(),
                            challenge.getStartDate(),
                            challenge.getEndDate(),
                            challenge.getMainTitle(),
                            challenge.getSubTitle(),
                            challenge.getContent(),
                            challenge.getSavingAmount(),
                            (long) challenge.getChallengeLogList().size()
                    );
                })
                .collect(Collectors.toList());

        // Dto로 변환 후 리턴.
        return GetChallengeListResDto.builder()
                .challengCnt((long) challengeList.size())
                .challengeList(challengeList)
                .build();
    }

    public void joinChallenge(String idToken, Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(CChallengeNotFoundException::new);
        User user = userRepository.findByIdToken(idToken).orElseThrow(CUserNotFoundException::new);
        if(challengeLogRepository.findByChallengeAndUser(challenge, user).isPresent()) {
            throw new CUserAlreadyJoinedException();
        }
        ChallengeLog log = new ChallengeLog();
        log.joinChallenge(user, challenge);
        challengeLogRepository.save(log);
    }

    public void leftChallenge(String idToken, Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(CChallengeNotFoundException::new);
        User user = userRepository.findByIdToken(idToken).orElseThrow(CUserAlreadyJoinedException::new);
        challengeLogRepository.deleteByChallengeAndUser(challenge, user);
    }

    public void deleteChallenge(Long challengeId) {
        try {
            challengeRepository.deleteById(challengeId);
        } catch (IllegalArgumentException e) {
            throw new CChallengeNotFoundException();
        }
    }

    public void updateChallenge(Long challengeId, UpdateChallengeReqDto updated) {
        Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(CChallengeNotFoundException::new);
        challenge.updateChallenge(updated);
        challengeRepository.save(challenge);
    }
}
