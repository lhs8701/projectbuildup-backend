package projectbuildup.saver.domain.challenge.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectbuildup.saver.domain.challenge.service.interfaces.ChallengeService;
import projectbuildup.saver.domain.dto.req.CreateChallengeReqDto;
import projectbuildup.saver.domain.dto.res.ViewChallengeResDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/challenge")
public class ChallengeController {

    private final ChallengeService challengeService;

    @GetMapping("/{challengeId}")
    public ResponseEntity<ViewChallengeResDto> viewChallenge(@PathVariable Long challengeId) {

        try {
            ViewChallengeResDto viewChallengeResDto = challengeService.viewChallenge(challengeId);

            return new ResponseEntity<>(viewChallengeResDto, HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("")
    public ResponseEntity<Void> createChallenge(@RequestBody CreateChallengeReqDto challengeReqDto) {
        try{
            challengeService.createChallenge(challengeReqDto);

            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

}
