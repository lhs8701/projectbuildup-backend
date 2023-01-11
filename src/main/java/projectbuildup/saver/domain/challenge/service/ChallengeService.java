package projectbuildup.saver.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.challenge.entity.Challenge;
import projectbuildup.saver.domain.challenge.error.exception.CChallengeNotFoundException;
import projectbuildup.saver.domain.challenge.repository.ChallengeJpaRepository;
import projectbuildup.saver.domain.dto.req.CreateChallengeRequestDto;
import projectbuildup.saver.domain.dto.req.UpdateChallengeReqDto;
import projectbuildup.saver.domain.dto.res.GetChallengeListResDto;
import projectbuildup.saver.domain.dto.res.ParticipantsResponseDto;
import projectbuildup.saver.domain.dto.res.ChallengeResponseDto;
import projectbuildup.saver.domain.dto.res.ParticipantDto;
import projectbuildup.saver.domain.participation.entity.Participation;
import projectbuildup.saver.domain.participation.service.ParticipationFindService;
import projectbuildup.saver.domain.participation.service.ParticipationService;
import projectbuildup.saver.domain.remittance.service.RemittanceService;
import projectbuildup.saver.domain.user.entity.Member;
import projectbuildup.saver.domain.user.service.UserFindService;
import projectbuildup.saver.global.util.StringDateConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeJpaRepository challengeJpaRepository;
    private final ChallengeFindService challengeFindService;

    private final ParticipationFindService participationFindService;
    private final ParticipationService participationService;
    private final UserFindService userFindService;
    private final RemittanceService remittanceService;
    private final StringDateConverter stringDateConverter;

    private final ChallengeSortService challengeSortService;

    public ParticipantsResponseDto getChallengeParticipants(Long challengeId) {
        Challenge challenge = challengeFindService.findById(challengeId);
        List<Participation> participationList = participationFindService.findAllByChallenge(challenge);
        List<ParticipantDto> responseDtoList = new ArrayList<>();
        for (Participation c : participationList) {
            Member member = userFindService.findById(c.getId());
            Long totalAmount = remittanceService.calculateSum(member, challenge);
            ParticipantDto participantDto = new ParticipantDto(member, totalAmount);
            responseDtoList.add(participantDto);
        }
        sortByTotalAmountDesc(responseDtoList);
        return new ParticipantsResponseDto(responseDtoList);
    }

    private void sortByTotalAmountDesc(List<ParticipantDto> responseDtoList) {
        Comparator<ParticipantDto> comparator = (p1, p2) -> {
            long difference = (p1.getTotalAmount() - p2.getTotalAmount());
            return (int) difference;
        };

        responseDtoList.sort(comparator);
    }

    public void createChallenge(CreateChallengeRequestDto challengeReqDto) {
        Challenge challenge = challengeReqDto.toEntity();

        challengeJpaRepository.save(challenge);
    }

    public ChallengeResponseDto getChallenge(Long challengeId) {
        Challenge challenge = challengeFindService.findById(challengeId);
        return new ChallengeResponseDto(challenge);
    }

    public void joinChallenge(Long memberId, Long challengeId) {
        Challenge challenge = challengeFindService.findById(challengeId);
        Member member = userFindService.findById(memberId);
        participationService.validateParticipationExistence(challenge, member);
        participationService.makeParticipation(challenge, member);
    }

    public void giveUpChallenge(Long memberId, Long challengeId) {
        Challenge challenge = challengeFindService.findById(challengeId);
        Member member = userFindService.findById(memberId);
        participationService.deleteByChallengeAndUser(challenge, member);
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

    public GetChallengeListResDto getAvailableChallenges(int sortType, boolean ascending, Long memberId) {
        List<Challenge> challenges = challengeFindService.findAll();
        List<Challenge> availableChallenges = challenges
                .stream()
                .filter(challenge -> isJoinable(challenge, memberId))
                .collect(Collectors.toList());

        sortByType(sortType, ascending, availableChallenges);
        List<ChallengeResponseDto> sortedAvailableChallenges = availableChallenges
                .stream()
                .map(ChallengeResponseDto::new)
                .collect(Collectors.toList());

        return new GetChallengeListResDto(sortedAvailableChallenges);
    }

    private void sortByType(int sortType, boolean ascending, List<Challenge> challenges) {
        if (sortType == 1) {
            challengeSortService.sortByParticipantNumber(ascending, challenges);
            return;
        }
        if (sortType == 2) {
            challengeSortService.sortByTotalAmount(ascending, challenges);
            return;
        }
        challengeSortService.sortByEndDate(ascending, challenges);
    }

    private boolean isJoinable(Challenge challenge, Long memberId) {
        for (Participation c : challenge.getParticipationList()) {
            Member member = userFindService.findById(c.getMember().getId());
            return memberId == member.getId();
        }
        return true;
    }


    public GetChallengeListResDto getMyChallenges(Long memberId) {
        List<Challenge> challenges = challengeFindService.findAll();
        Member member = userFindService.findById(memberId);
        List<Participation> participations = participationFindService.findAllByUser(member);

        List<ChallengeResponseDto> myChallenges = participations
                .stream()
                .map(participation -> new ChallengeResponseDto(participation.getChallenge()))
                .collect(Collectors.toList());
        return new GetChallengeListResDto(myChallenges);
    }
}
