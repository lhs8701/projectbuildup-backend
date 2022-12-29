package projectbuildup.saver.domain.challenge.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import projectbuildup.saver.domain.challenge.repository.ChallengeRepository;
import projectbuildup.saver.domain.challenge.service.interfaces.ChallengeService;
import projectbuildup.saver.domain.challengeLog.repository.ChallengeLogRepository;
import projectbuildup.saver.domain.challengeRecord.repository.ChallengeRecordRepository;
import projectbuildup.saver.domain.user.repository.UserRepository;




class ChallengeServiceImplTest {

    // Unit test 에 사용될 Mock
    @Mock
    ChallengeRepository challengeRepository;

    @Mock
    ChallengeLogRepository challengeLogRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    ChallengeRecordRepository challengeRecordRepository;

    // Unit test 대상
    @InjectMocks
    ChallengeService challengeService;


}