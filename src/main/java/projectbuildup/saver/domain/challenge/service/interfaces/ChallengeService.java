package projectbuildup.saver.domain.challenge.service.interfaces;

import projectbuildup.saver.domain.dto.res.ViewChallengeResDto;

public interface ChallengeService {

    ViewChallengeResDto viewChallenge(Long challengeId);
}
