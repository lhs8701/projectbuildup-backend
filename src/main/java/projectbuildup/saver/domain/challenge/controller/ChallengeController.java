package projectbuildup.saver.domain.challenge.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectbuildup.saver.domain.challenge.service.ChallengeService;
import projectbuildup.saver.domain.dto.req.CreateChallengeRequestDto;
import projectbuildup.saver.domain.dto.req.JoinChallengeReqDto;
import projectbuildup.saver.domain.dto.req.LeftChallengeReqDto;
import projectbuildup.saver.domain.dto.req.UpdateChallengeReqDto;
import projectbuildup.saver.domain.dto.res.ChallengeResponseDto;
import projectbuildup.saver.domain.dto.res.GetChallengeListResDto;
import projectbuildup.saver.domain.dto.res.ParticipantsResponseDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/challenge")
@Slf4j
@CrossOrigin("*")
public class ChallengeController {

    private final ChallengeService challengeService;

    @PostMapping("")
    public ResponseEntity<Void> createChallenge(@RequestBody CreateChallengeRequestDto challengeReqDto) {
        challengeService.createChallenge(challengeReqDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/join")
    public ResponseEntity<HttpStatus> joinChallenge(@RequestBody JoinChallengeReqDto joinChallengeReqDto) {
        challengeService.joinChallenge(joinChallengeReqDto.getLoginId(), joinChallengeReqDto.getChallengeId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{challengeId}/participants")
    public ResponseEntity<ParticipantsResponseDto> getChallengeParticipants(@PathVariable Long challengeId) {
        ParticipantsResponseDto getChallengeParticipantsResDto = challengeService.getChallengeParticipants(challengeId);
        log.info(getChallengeParticipantsResDto.toString());
        return new ResponseEntity<>(getChallengeParticipantsResDto, HttpStatus.OK);
    }

    @GetMapping("/{challengeId}")
    public ResponseEntity<ChallengeResponseDto> getChallenge(@PathVariable Long challengeId) {
        ChallengeResponseDto getChallengeResDto = challengeService.getChallenge(challengeId);
        log.info(getChallengeResDto.toString());
        return new ResponseEntity<>(getChallengeResDto, HttpStatus.OK);
    }

    @PostMapping("/left")
    public ResponseEntity<HttpStatus> leftChallenge(@RequestBody LeftChallengeReqDto leftChallengeReqDto) {
        challengeService.giveUpChallenge(leftChallengeReqDto.getLoginId(), leftChallengeReqDto.getChallengeId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{challengeId}")
    public ResponseEntity<HttpStatus> deleteChallenge(@PathVariable Long challengeId) {
        challengeService.deleteChallenge(challengeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{challengeId}")
    public ResponseEntity<HttpStatus> updateChallenge(@PathVariable Long challengeId, @RequestBody UpdateChallengeReqDto updated) {
        challengeService.updateChallenge(challengeId, updated);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<GetChallengeListResDto> getAvailableChallenges(@RequestParam int sort, @RequestParam boolean ascending, @RequestParam String loginId) {
        GetChallengeListResDto challenges = challengeService.getAvailableChallenges(sort, ascending, loginId);
        return new ResponseEntity<>(challenges, HttpStatus.OK);
    }
//
//    @GetMapping("/my")
//    public ResponseEntity<GetChallengeListResDto> getMyChallenges(@RequestParam String loginId) {
//        GetChallengeListResDto challenges = challengeService.getMyChallenges(loginId);
//        if (challenges != null) {
//            return new ResponseEntity<>(challenges, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        }
//    }

}
