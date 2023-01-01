package projectbuildup.saver.domain.challengeRecord.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectbuildup.saver.domain.challengeRecord.service.RemittanceService;
import projectbuildup.saver.domain.dto.req.SaveSavingReqDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/challengeRecord")
public class RemittanceController {

    private final RemittanceService remittanceService;

    @PostMapping("")
    public ResponseEntity<Void> saveSaving(@RequestBody SaveSavingReqDto saveSavingReqDto) {
        remittanceService.saveSaving(saveSavingReqDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
