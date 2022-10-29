package projectbuildup.saver.domain.user.service;

import projectbuildup.saver.domain.dto.req.CreateUserReqDto;

public interface UserService {

    void createUser(CreateUserReqDto createUserReqDto);
}
