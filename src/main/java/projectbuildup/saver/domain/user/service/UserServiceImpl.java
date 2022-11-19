package projectbuildup.saver.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import projectbuildup.saver.domain.dto.req.CreateUserReqDto;
import projectbuildup.saver.domain.dto.res.GetUserResDto;
import projectbuildup.saver.domain.user.entity.UserEntity;
import projectbuildup.saver.domain.user.repository.UserRepository;
import projectbuildup.saver.domain.user.service.interfaces.UserService;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void createUser(CreateUserReqDto createUserReqDto) {
        UserEntity userEntity = UserEntity.builder()
                .loginId(createUserReqDto.getLoginId())
                .password(createUserReqDto.getPassword())
                .nickName(createUserReqDto.getNickName())
                .phoneNumber(createUserReqDto.getPhoneNumber())
                .build();

        userRepository.save(userEntity);
    }

    @Override
    public GetUserResDto getUser(String loginId) {
        UserEntity user = userRepository.findByLoginId(loginId).orElseThrow();
        GetUserResDto getUserResDto = GetUserResDto
                .builder()
                .nickname(user.getNickName())
                .build();
        return getUserResDto;
    }

    @Override
    public void updateUser(String loginId, String nickname) {
        UserEntity user = userRepository.findByLoginId(loginId).orElseThrow();
        user.setNickName(nickname);
        userRepository.save(user);
    }

}
