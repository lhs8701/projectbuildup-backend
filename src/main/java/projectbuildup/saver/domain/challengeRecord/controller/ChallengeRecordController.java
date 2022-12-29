package projectbuildup.saver.domain.challengeRecord.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectbuildup.saver.domain.dto.req.SaveSavingReqDto;
import projectbuildup.saver.domain.challengeRecord.service.ChallengeRecordService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/challengeRecord")
public class ChallengeRecordController {

    private final ChallengeRecordService challengeRecordService;

    @PostMapping("")
    public ResponseEntity<Void> saveSaving(@RequestBody SaveSavingReqDto saveSavingReqDto) {
        challengeRecordService.saveSaving(saveSavingReqDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
