package projectbuildup.saver.domain.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectbuildup.saver.domain.dto.req.CreateUserReqDto;
import projectbuildup.saver.domain.dto.req.UpdateUserResDto;
import projectbuildup.saver.domain.dto.res.GetChallengeResDto;
import projectbuildup.saver.domain.dto.res.GetUserResDto;
import projectbuildup.saver.domain.user.service.interfaces.UserService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final UserService userService;

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
