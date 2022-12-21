package projectbuildup.saver.domain.challenge.service.interfaces;

import projectbuildup.saver.domain.dto.req.CreateChallengeReqDto;
import projectbuildup.saver.domain.dto.res.GetChallengeParticipantsResDto;
import projectbuildup.saver.domain.dto.res.GetChallengeResDto;

import java.util.List;

public interface ChallengeService {
    /**
     * 챌린지에 참여한 사람들의 명단을 리턴함.
     * @param {challengeId} Long - 챌린지 ID
     * @return {ViewChallengeResDto} - 사람수와 명단, 명단에는 아낀 금액, 최근 랭킹, 닉네임이 있음
     */
    GetChallengeParticipantsResDto getChallengeParticipants(Long challengeId);

    /**
     * 챌린지 생성
     * @param {challengeReqDto} - 챌린지 생성에 필요한 정보가 있음.
     */
    void createChallenge(CreateChallengeReqDto challengeReqDto);

    /**
     * 현재 참여 가능한 챌린지를 모두 보여줌, 자신이 참여한것 제외, 특정 타입으로 정렬해야함
     * @param sortType {Long} 1 -> 참여자 수 2 -> 송금 금액 3 -> 종료일자
     * @param ascending {Boolean} 오름차순인지 내림차순인지
     * @return
     */
    List<GetChallengeResDto> getAvailableChallenges(Long sortType, Boolean ascending, String loginId);

    /**
     * 단일 챌린지 리턴.
     * @param challengeId {Long} - 챌린지 아이디
     * @return
     */
    GetChallengeResDto getChallenge(Long challengeId);

    /**
     * 특정 유저가 참여중인 챌린지들의 리스트를 리턴합니다.
     * @param loginId {String} 유저 로그인 아아디
     * @return 유저가 참여중인 챌린지들의 리스트
     */
    List<GetChallengeResDto> getMyChallenges(String loginId);

    /**
     * 챌린지 참여
     * @param loginId {String} 유저 아이디
     * @param challengeId {Long} 챌린지 아이디 
     */
    void joinChallenge(String idToken, Long challengeId);

    /**
     * 챌린지 탈퇴
     * @param loginId {String} 유저 아이디
     * @param challengeId {Long} 챌린지 아이디
     */
    void leftChallenge(String loginId, Long challengeId);

}
