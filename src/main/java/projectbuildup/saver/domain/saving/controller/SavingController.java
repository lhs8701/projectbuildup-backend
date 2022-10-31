package projectbuildup.saver.domain.saving.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectbuildup.saver.domain.dto.req.SaveSavingReqDto;
import projectbuildup.saver.domain.dto.res.ViewChallengeResDto;
import projectbuildup.saver.domain.saving.service.SavingService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/saving")
public class SavingController {

    private final SavingService savingService;

    @PostMapping("")
    public ResponseEntity<Void> saveSaving(@RequestBody SaveSavingReqDto saveSavingReqDto) {

        try {
            savingService.saveSaving(saveSavingReqDto);

            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

}
