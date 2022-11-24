package projectbuildup.saver.domain.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.saver.domain.dto.req.CreateUserReqDto;
import projectbuildup.saver.domain.dto.req.UpdateUserResDto;
import projectbuildup.saver.domain.dto.res.GetUserResDto;
import projectbuildup.saver.domain.user.dto.PasswordUpdateParam;
import projectbuildup.saver.domain.user.dto.ProfileUpdateParam;
import projectbuildup.saver.domain.user.entity.UserEntity;
import projectbuildup.saver.domain.user.service.interfaces.UserService;
import projectbuildup.saver.global.common.ConstValue;

@Api(tags = {"User"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "회원 비밀번호 변경",
            notes = """
                    회원의 비밀번호를 변경합니다.
                    \nparameter : 변경할 비밀번호
                    \nresponse : X
                    """
    )
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('User')")
    @PatchMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordUpdateParam passwordUpdateParam, @AuthenticationPrincipal UserEntity user) {
        userService.changePassword(passwordUpdateParam, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "회원 프로필 수정",
            notes = """
                    회원의 프로필을 변경합니다.
                    \nparameter : 변경할 항목(닉네임)
                    \nresponse : 변경한 회원의 아이디
                    """
    )
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('User')")
    @PatchMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody ProfileUpdateParam profileUpdateParam, @AuthenticationPrincipal UserEntity user) {
        return new ResponseEntity<>(userService.updateProfile(profileUpdateParam, user), HttpStatus.OK);
    }

    @ApiOperation(value = "회원 프로필 이미지 변경",
            notes = """
                    회원의 프로필 이미지를 변경합니다.
                    \nparameter : 변경할 항목(닉네임)
                    \nresponse : 변경한 회원의 아이디
                    """
    )
    @ApiImplicitParam(name = ConstValue.JWT_HEADER, value = "AccessToken", required = true, dataType = "String", paramType = "header")
    @PreAuthorize("hasRole('User')")
    @PostMapping("/profile/image")
    public ResponseEntity<?> changeProfileImage(@RequestPart MultipartFile imageFile, @AuthenticationPrincipal UserEntity user) {
        return new ResponseEntity<>(userService.changeProfileImage(imageFile, user), HttpStatus.OK);
    }



    @PostMapping("")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserReqDto createUserReqDto) {
        userService.createUser(createUserReqDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<GetUserResDto> getUser(@RequestParam String loginId) {
        GetUserResDto getUserResDto = userService.getUser(loginId);
        return new ResponseEntity<>(getUserResDto, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody UpdateUserResDto updateUserResDto) {
        userService.updateUser(updateUserResDto.getLoginId(), updateUserResDto.getNickname());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
