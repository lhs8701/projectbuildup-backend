package projectbuildup.saver.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.saver.domain.challenge.entity.Challenge;
import projectbuildup.saver.domain.challenge.error.exception.CChallengeNotFoundException;
import projectbuildup.saver.domain.challenge.repository.ChallengeJpaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeFindService {
    private final ChallengeJpaRepository challengeJpaRepository;

    public Challenge findById(Long challengeId){
        return challengeJpaRepository.findById(challengeId).orElseThrow(CChallengeNotFoundException::new);
    }
    public List<Challenge> findAll(){
        return challengeJpaRepository.findAll();
    }
}
