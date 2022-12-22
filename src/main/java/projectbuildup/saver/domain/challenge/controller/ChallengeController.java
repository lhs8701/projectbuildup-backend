package projectbuildup.saver.domain.challenge.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectbuildup.saver.domain.challenge.service.interfaces.ChallengeService;
import projectbuildup.saver.domain.dto.req.CreateChallengeReqDto;
import projectbuildup.saver.domain.dto.req.JoinChallengeReqDto;
import projectbuildup.saver.domain.dto.req.LeftChallengeReqDto;
import projectbuildup.saver.domain.dto.res.GetChallengeListResDto;
import projectbuildup.saver.domain.dto.res.GetChallengeParticipantsResDto;
import projectbuildup.saver.domain.dto.res.GetChallengeResDto;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/challenge")
@Slf4j
@CrossOrigin("*")
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping("")
    public ResponseEntity<Void> createChallenge(@RequestBody CreateChallengeReqDto challengeReqDto) {
        challengeService.createChallenge(challengeReqDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/join")
    public ResponseEntity<HttpStatus> joinChallenge(@RequestBody JoinChallengeReqDto joinChallengeReqDto) {
        challengeService.joinChallenge(joinChallengeReqDto.getLoginId(), joinChallengeReqDto.getChallengeId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{challengeId}/participants")
    public ResponseEntity<GetChallengeParticipantsResDto> getChallengeParticipants(@PathVariable Long challengeId) {
        GetChallengeParticipantsResDto getChallengeParticipantsResDto = challengeService.getChallengeParticipants(challengeId);
        log.info(getChallengeParticipantsResDto.toString());
        return new ResponseEntity<>(getChallengeParticipantsResDto, HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<GetChallengeListResDto> getAvailableChallenges(@RequestParam Long sortType, @RequestParam Boolean ascending, @RequestParam String loginId) {
        GetChallengeListResDto challenges = challengeService.getAvailableChallenges(sortType, ascending, loginId);
        if (challenges != null) {
            return new ResponseEntity<>(challenges, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/my")
    public ResponseEntity<GetChallengeListResDto> getMyChallenges(@RequestParam String loginId) {
        GetChallengeListResDto challenges = challengeService.getMyChallenges(loginId);
        if (challenges != null) {
            return new ResponseEntity<>(challenges, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{challengeId}")
    public ResponseEntity<GetChallengeResDto> getChallenge(@PathVariable Long challengeId) {
        GetChallengeResDto getChallengeResDto = challengeService.getChallenge(challengeId);
        log.info(getChallengeResDto.toString());
        return new ResponseEntity<>(getChallengeResDto, HttpStatus.OK);
    }

    @DeleteMapping("")
    public ResponseEntity<HttpStatus> leftChallenge(@RequestBody LeftChallengeReqDto leftChallengeReqDto) {
        challengeService.leftChallenge(leftChallengeReqDto.getLoginId(), leftChallengeReqDto.getChallengeId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
