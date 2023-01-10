package projectbuildup.saver.domain.user.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.saver.domain.recentremittance.service.RecentRemittanceService;
import projectbuildup.saver.domain.user.dto.PasswordUpdateParam;
import projectbuildup.saver.domain.user.dto.ProfileUpdateParam;
import projectbuildup.saver.domain.user.dto.UserIdRequestParam;
import projectbuildup.saver.domain.user.service.MemberService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final MemberService memberService;
    private final RecentRemittanceService recentRemittanceService;


    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordUpdateParam passwordUpdateParam) {
        memberService.changePassword(passwordUpdateParam);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody ProfileUpdateParam profileUpdateParam) {
        return new ResponseEntity<>(memberService.updateProfile(profileUpdateParam), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestBody UserIdRequestParam userIdRequestParam) {
        return new ResponseEntity<>(memberService.getProfile(userIdRequestParam), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/profile/image")
    public ResponseEntity<?> changeProfileImage(@RequestBody UserIdRequestParam userIdRequestParam, @RequestPart MultipartFile imageFile) {
        return new ResponseEntity<>(memberService.changeProfileImage(userIdRequestParam, imageFile), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{idToken}/recent")
    public ResponseEntity<?> getSavingStatus(@PathVariable String idToken) {
        return new ResponseEntity<>(recentRemittanceService.getRecentSaving(idToken), HttpStatus.OK);
    }
}
