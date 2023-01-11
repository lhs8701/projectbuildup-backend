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
import projectbuildup.saver.domain.dto.res.ParticipantsResponseDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/challenges")
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
        challengeService.joinChallenge(joinChallengeReqDto.getMemberId(), joinChallengeReqDto.getChallengeId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/left")
    public ResponseEntity<HttpStatus> leftChallenge(@RequestBody LeftChallengeReqDto leftChallengeReqDto) {
        challengeService.giveUpChallenge(leftChallengeReqDto.getMemberId(), leftChallengeReqDto.getChallengeId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{challengeId}")
    public ResponseEntity<ChallengeResponseDto> getChallenge(@PathVariable Long challengeId) {
        ChallengeResponseDto getChallengeResDto = challengeService.getChallenge(challengeId);
        return new ResponseEntity<>(getChallengeResDto, HttpStatus.OK);
    }

    @PutMapping("/{challengeId}")
    public ResponseEntity<HttpStatus> updateChallenge(@PathVariable Long challengeId, @RequestBody UpdateChallengeReqDto updated) {
        challengeService.updateChallenge(challengeId, updated);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{challengeId}")
    public ResponseEntity<HttpStatus> deleteChallenge(@PathVariable Long challengeId) {
        challengeService.deleteChallenge(challengeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{challengeId}/participants")
    public ResponseEntity<ParticipantsResponseDto> getChallengeParticipants(@PathVariable Long challengeId) {
        ParticipantsResponseDto getChallengeParticipantsResDto = challengeService.getChallengeParticipants(challengeId);
        return new ResponseEntity<>(getChallengeParticipantsResDto, HttpStatus.OK);
    }
}
