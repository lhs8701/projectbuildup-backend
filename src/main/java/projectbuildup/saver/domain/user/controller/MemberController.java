package projectbuildup.saver.domain.user.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.saver.domain.challenge.service.ChallengeService;
import projectbuildup.saver.domain.dto.res.GetChallengeListResDto;
import projectbuildup.saver.domain.recentremittance.service.RecentRemittanceService;
import projectbuildup.saver.domain.user.dto.PasswordUpdateParam;
import projectbuildup.saver.domain.user.dto.ProfileUpdateParam;
import projectbuildup.saver.domain.user.service.MemberService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final RecentRemittanceService recentRemittanceService;
    private final ChallengeService challengeService;


    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordUpdateParam passwordUpdateParam) {
        memberService.changePassword(passwordUpdateParam);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{memberId}/profile")
    public ResponseEntity<?> getProfile(@PathVariable Long memberId) {
        return new ResponseEntity<>(memberService.getProfile(memberId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/{memberId}/profile")
    public ResponseEntity<?> updateProfile(@PathVariable Long memberId, @RequestBody ProfileUpdateParam profileUpdateParam) {
        return new ResponseEntity<>(memberService.updateProfile(memberId, profileUpdateParam), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{memberId}/profile/image")
    public ResponseEntity<?> changeProfileImage(@PathVariable Long memberId, @RequestPart MultipartFile imageFile) {
        return new ResponseEntity<>(memberService.changeProfileImage(memberId, imageFile), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{memberId}/remittance")
    public ResponseEntity<?> getRecentRemittance(@PathVariable String idToken) {
        return new ResponseEntity<>(recentRemittanceService.getRecentRemittance(idToken), HttpStatus.OK);
    }

    @GetMapping("/{memberId}/challenges/available")
    public ResponseEntity<GetChallengeListResDto> getAvailableChallenges(@RequestParam int sort, @RequestParam boolean ascending, @RequestParam String loginId) {
        return new ResponseEntity<>(challengeService.getAvailableChallenges(sort, ascending, loginId), HttpStatus.OK);
    }

    @GetMapping("/{memberId}/challenges")
    public ResponseEntity<GetChallengeListResDto> getMyChallenges(@RequestParam String idToken) {
        return new ResponseEntity<>(challengeService.getMyChallenges(idToken), HttpStatus.OK);
    }
}
