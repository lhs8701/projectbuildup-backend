package projectbuildup.saver.domain.challenge.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectbuildup.saver.domain.challenge.service.interfaces.ChallengeService;
import projectbuildup.saver.domain.dto.req.CreateChallengeReqDto;
import projectbuildup.saver.domain.dto.req.GetAvailableChallengesReqDto;
import projectbuildup.saver.domain.dto.req.GetMyChallengesReqDto;
import projectbuildup.saver.domain.dto.res.GetChallengeParticipantsResDto;
import projectbuildup.saver.domain.dto.res.GetChallengeResDto;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/challenge")
@Slf4j
public class ChallengeController {

    private final ChallengeService challengeService;

    @GetMapping("/{challengeId}/participants")
    public ResponseEntity<GetChallengeParticipantsResDto> getChallengeParticipants(@PathVariable Long challengeId) {
        try {
            GetChallengeParticipantsResDto getChallengeParticipantsResDto = challengeService.getChallengeParticipants(challengeId);

            return new ResponseEntity<>(getChallengeParticipantsResDto, HttpStatus.OK);
        }catch(Exception e) {
            log.info(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("")
    public ResponseEntity<Void> createChallenge(@RequestBody CreateChallengeReqDto challengeReqDto) {
        try{
            challengeService.createChallenge(challengeReqDto);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e) {
            log.info(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/available")
    public ResponseEntity<List<GetChallengeResDto>> getAvailableChallenges(@RequestBody GetAvailableChallengesReqDto getChallengesDto) {
        try {
            List<GetChallengeResDto> challenges = challengeService.getAvailableChallenges(getChallengesDto.getSortType(), getChallengesDto.isAscending(), getChallengesDto.getLoginId());
            if(challenges != null) {
                return new ResponseEntity<>(challenges, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.info(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<GetChallengeResDto>> getMyChallenges(@RequestBody GetMyChallengesReqDto getMyChallengesReqDto) {
        try {
            List<GetChallengeResDto> challenges = challengeService.getMyChallenges(getMyChallengesReqDto.getLoginId());
            if(challenges != null) {
                return new ResponseEntity<>(challenges, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            log.info(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/{challengeId}")
    public ResponseEntity<GetChallengeResDto> getChallenge(@PathVariable Long challengeId) {
        try {
            GetChallengeResDto getChallengeResDto = challengeService.getChallenge(challengeId);
            return new ResponseEntity<>(getChallengeResDto, HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

    }

}
