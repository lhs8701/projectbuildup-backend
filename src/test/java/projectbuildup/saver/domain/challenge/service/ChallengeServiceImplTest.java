package projectbuildup.saver.domain.challenge.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import projectbuildup.saver.domain.challenge.repository.ChallengeRepository;
import projectbuildup.saver.domain.participation.repository.ParticipationJpaRepository;
import projectbuildup.saver.domain.challengeRecord.repository.RemittanceJpaRepository;
import projectbuildup.saver.domain.user.repository.UserRepository;




class ChallengeServiceImplTest {

    // Unit test 에 사용될 Mock
    @Mock
    ChallengeRepository challengeRepository;

    @Mock
    ParticipationJpaRepository participationJpaRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    RemittanceJpaRepository remittanceJpaRepository;

    // Unit test 대상
    @InjectMocks
    ChallengeService challengeService;

}