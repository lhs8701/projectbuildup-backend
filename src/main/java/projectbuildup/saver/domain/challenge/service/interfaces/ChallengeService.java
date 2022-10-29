package projectbuildup.saver.domain.challenge.service.interfaces;

import org.springframework.http.ResponseEntity;
import projectbuildup.saver.domain.dto.req.CreateChallengeReqDto;
import projectbuildup.saver.domain.dto.res.ViewChallengeResDto;

public interface ChallengeService {

    ViewChallengeResDto viewChallenge(Long challengeId);
    void createChallenge(CreateChallengeReqDto challengeReqDto);
}
