package projectbuildup.saver.domain.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
import projectbuildup.saver.domain.user.service.UserService;
import projectbuildup.saver.global.common.ConstValue;

@Api(tags = {"User"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final RecentRemittanceService recentRemittanceService;

    @ApiOperation(value = "회원 비밀번호 변경",
            notes = """
                    회원의 비밀번호를 변경합니다.
                    \nparameter : 아이디 토큰, 변경할 비밀번호
                    \nresponse : X
                    """
    )
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordUpdateParam passwordUpdateParam) {
        userService.changePassword(passwordUpdateParam);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "회원 프로필 수정",
            notes = """
                    회원의 프로필을 변경합니다.
                    \nparameter : 아이디 토큰, 변경할 항목(닉네임)
                    \nresponse : 변경한 회원의 아이디
                    """
    )
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @PatchMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody ProfileUpdateParam profileUpdateParam) {
        return new ResponseEntity<>(userService.updateProfile(profileUpdateParam), HttpStatus.OK);
    }

    @ApiOperation(value = "회원 프로필 조회",
            notes = """
                    회원의 프로필을 조회합니다.
                    \nparameter : 아이디 토큰
                    \nresponse : 회원의 프로필
                    """
    )
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestBody UserIdRequestParam userIdRequestParam) {
        return new ResponseEntity<>(userService.getProfile(userIdRequestParam), HttpStatus.OK);
    }

    @ApiOperation(value = "회원 프로필 이미지 변경",
            notes = """
                    회원의 프로필 이미지를 변경합니다.
                    \nparameter : 아이디 토큰, 변경할 항목(닉네임)
                    \nresponse : 변경한 회원의 아이디
                    """
    )
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/profile/image")
    public ResponseEntity<?> changeProfileImage(@RequestBody UserIdRequestParam userIdRequestParam, @RequestPart MultipartFile imageFile) {
        return new ResponseEntity<>(userService.changeProfileImage(userIdRequestParam, imageFile), HttpStatus.OK);
    }

    @ApiOperation(value = "현재 절약 상황 조회",
            notes = """
                    현재 회원의 절약 상황을 조회합니다.
                    \nparameter : 아이디 토큰
                    \nresponse : 회원의 절약 상황
                    """
    )
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{idToken}/recent")
    public ResponseEntity<?> getSavingStatus(@PathVariable String idToken) {
        return new ResponseEntity<>(recentRemittanceService.getRecentSaving(idToken), HttpStatus.OK);
    }
}
