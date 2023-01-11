package projectbuildup.saver.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.user.entity.Member;
import projectbuildup.saver.domain.user.error.exception.CUserExistException;
import projectbuildup.saver.domain.user.error.exception.CResourceNotFoundException;
import projectbuildup.saver.domain.user.repository.MemberJpaRepository;

@RequiredArgsConstructor
@Service
public class UserFindService {

    private final MemberJpaRepository memberJpaRepository;

    public Member findByIdToken(String idToken){
        return memberJpaRepository.findByIdToken(idToken).orElseThrow(CResourceNotFoundException::new);
    }

    public Member findById(Long userId){
        return memberJpaRepository.findById(userId).orElseThrow(CResourceNotFoundException::new);
    }

    public void validateUserExistence(String idToken){
        if (memberJpaRepository.findByIdToken(idToken).isPresent()) {
            throw new CUserExistException();
        }
    }
}
