package projectbuildup.saver.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.user.entity.User;
import projectbuildup.saver.domain.user.error.exception.CUserExistException;
import projectbuildup.saver.domain.user.error.exception.CUserNotFoundException;
import projectbuildup.saver.domain.user.repository.UserJpaRepository;

@RequiredArgsConstructor
@Service
public class UserFindService {

    private final UserJpaRepository userJpaRepository;

    public User findByIdToken(String idToken){
        return userJpaRepository.findByIdToken(idToken).orElseThrow(CUserNotFoundException::new);
    }

    public void validateUserExistence(String idToken){
        if (userJpaRepository.findByIdToken(idToken).isPresent()) {
            throw new CUserExistException();
        }
    }
}
