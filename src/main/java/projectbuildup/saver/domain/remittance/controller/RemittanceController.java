package projectbuildup.saver.domain.remittance.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectbuildup.saver.domain.remittance.service.RemittanceService;
import projectbuildup.saver.domain.dto.req.RemitRequestDto;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/remittance")
public class RemittanceController {

    private final RemittanceService remittanceService;

    @PostMapping("")
    public ResponseEntity<Void> saveSaving(@RequestBody RemitRequestDto remitRequestDto) {
        remittanceService.remit(remitRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
