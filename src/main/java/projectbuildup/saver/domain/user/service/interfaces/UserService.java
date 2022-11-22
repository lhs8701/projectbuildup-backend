package projectbuildup.saver.domain.user.service.interfaces;

import projectbuildup.saver.domain.dto.req.CreateUserReqDto;
import projectbuildup.saver.domain.dto.res.GetUserResDto;

public interface UserService {

    void createUser(CreateUserReqDto createUserReqDto);
    GetUserResDto getUser(String loginId);
    void updateUser(String loginId, String nickname);
}
