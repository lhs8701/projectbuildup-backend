package projectbuildup.saver.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.challenge.entity.ChallengeEntity;
import projectbuildup.saver.domain.challenge.repository.ChallengeRepository;
import projectbuildup.saver.domain.challenge.service.interfaces.ChallengeService;
import projectbuildup.saver.domain.challengeLog.entity.ChallengeLogEntity;
import projectbuildup.saver.domain.challengeLog.repository.ChallengeLogRepository;
import projectbuildup.saver.domain.dto.req.CreateChallengeReqDto;
import projectbuildup.saver.domain.dto.res.GetChallengeResDto;
import projectbuildup.saver.domain.dto.res.ParticipantResDto;
import projectbuildup.saver.domain.dto.res.GetChallengeParticipantsResDto;
import projectbuildup.saver.domain.saving.entity.SavingEntity;
import projectbuildup.saver.domain.saving.repository.SavingRepository;
import projectbuildup.saver.domain.user.entity.UserEntity;
import projectbuildup.saver.domain.user.repository.UserRepository;

import java.sql.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    public GetChallengeParticipantsResDto getChallengeParticipants(Long challengeId) {
        // 챌린지에 참여한 사람 확인
        List<ChallengeLogEntity> challengeLogEntityList = challengeLogRepository.findByChallenge_Id(challengeId);

        List<ParticipantResDto> participantResDtoList = new ArrayList<>();

        // 참여한 모든 유저에 대해 For Loop
        for(ChallengeLogEntity c : challengeLogEntityList) {
            // id를 통해 유저 Entity를 가져옴
            // 유저가 없을시 Exception throw -> 404 NOT FOUND
            UserEntity userEntity = userRepository.findById(c.getUser().getId()).orElseThrow();


            // 본 유저가 본 챌린지에 모았던 기록들을 모두 가져 온 후 총액을 계산함.
            List<SavingEntity> savingEntityList = savingRepository.findByChallengeIdAndUserId(challengeId, userEntity.getId());
            Long savingAmount = 0L;
            for(SavingEntity saving : savingEntityList) {
                savingAmount += saving.getAmount();
            }

            // 참여자 추가.
            ParticipantResDto participantResDto = ParticipantResDto.builder()
                    .loginId(userEntity.getLoginId())
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

    @Override
    public List<GetChallengeResDto> getAvailableChallenges(Long sortType, Boolean ascending, String loginId) {

        // 모든 챌린지 가져옴
        List<ChallengeEntity> challenges = challengeRepository.findAll();

        List<ChallengeEntity> selectedChallenges = new ArrayList<>(
                challenges
                        .stream()
                        .filter((challenge) -> {
                    // 챌린지 중 자신의 loginId가 있는 챌린지는 제외함.
                            for(ChallengeLogEntity c: challenge.getChallengeLogEntityList()) {
                                UserEntity user = userRepository.findById(c.getUser().getId()).orElseThrow();
                                return !loginId.equals(user.getLoginId());
                            }
                            return true;
                        })
                        .toList()
                );


        // sort 방식에 따라 정렬함.
        if(sortType == 1) {
            selectedChallenges.sort((a, b) -> {
                return ascending ? a.getChallengeLogEntityList().size() - b.getChallengeLogEntityList().size() : b.getChallengeLogEntityList().size() - a.getChallengeLogEntityList().size();
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

        // Dto로 변환 후 리턴.
        return (List<GetChallengeResDto>) selectedChallenges
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
                                                                        (long) challenge.getChallengeLogEntityList().size()
                                                                );
                                                            })
                                                            .toList();
    }

    @Override
    public GetChallengeResDto getChallenge(Long challengeId) {
        ChallengeEntity challenge = challengeRepository.findById(challengeId).orElseThrow();
        return new GetChallengeResDto(
                    challenge.getId(),
                    challenge.getStartDate(),
                    challenge.getEndDate(),
                    challenge.getMainTitle(),
                    challenge.getSubTitle(),
                    challenge.getContent(),
                    challenge.getSavingAmount(),
                    (long) challenge.getChallengeLogEntityList().size()
                );
    }

    @Override
    public List<GetChallengeResDto> getMyChallenges(String loginId) {
        // 전부 찾아서 loginId가 같은 user가 있는 챌린지만 추려낸 후 리턴.
        List<ChallengeEntity> challenges = challengeRepository.findAll();
        List<ChallengeEntity> userChallenges = challenges
                .stream()
                .filter((challenge) -> {
                    List<ChallengeLogEntity> challengeLogs = challenge.getChallengeLogEntityList();
                    for(ChallengeLogEntity c: challengeLogs) {
                        UserEntity user = userRepository.findById(c.getUser().getId()).orElseThrow();
                        if (loginId.equals(user.getLoginId())) {
                            return true;
                        }
                    }
                    return false;
                })
                .toList();
        return (List<GetChallengeResDto>) userChallenges
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
                            (long) challenge.getChallengeLogEntityList().size()
                    );
                })
                .toList();
    }

    @Override
    public void joinChallenge(String loginId, Long challengeId) {
        ChallengeEntity challenge = challengeRepository.findById(challengeId).orElseThrow();
        UserEntity user = userRepository.findByLoginId(loginId).orElseThrow();
        if(challengeLogRepository.findByChallengeAndUser(challenge, user).isPresent()) {
            log.info("already joined" + loginId + challengeId);
            return;
        }
        ChallengeLogEntity log = new ChallengeLogEntity();
        log.setChallenge(challenge);
        log.setUser(user);
        challengeLogRepository.save(log);
    }

    @Override
    public void leftChallenge(String loginId, Long challengeId) {
        ChallengeEntity challenge = challengeRepository.findById(challengeId).orElseThrow();
        UserEntity user = userRepository.findByLoginId(loginId).orElseThrow();
        challengeLogRepository.deleteByChallengeAndUser(challenge, user);
    }

}
