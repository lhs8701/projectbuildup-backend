package projectbuildup.saver.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.dto.req.CreateUserReqDto;
import projectbuildup.saver.domain.user.entity.UserEntity;
import projectbuildup.saver.domain.user.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

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
}
