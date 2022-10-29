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
        
        //Exception 추가
        ViewChallengeResDto viewChallengeResDto = challengeService.viewChallenge(challengeId);

        return new ResponseEntity<>(viewChallengeResDto, HttpStatus.OK);
    }

    /**
     * 챌린지 생성을 위한 메서드
     * @param challengeReqDto
     */
    public void createChallenge(@RequestBody CreateChallengeReqDto challengeReqDto) {

    }

}
