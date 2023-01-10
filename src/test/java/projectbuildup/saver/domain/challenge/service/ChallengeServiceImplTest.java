package projectbuildup.saver.domain.challenge.service;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import projectbuildup.saver.domain.challenge.repository.ChallengeJpaRepository;
import projectbuildup.saver.domain.participation.repository.ParticipationJpaRepository;
import projectbuildup.saver.domain.remittance.repository.RemittanceJpaRepository;
import projectbuildup.saver.domain.user.repository.MemberJpaRepository;


class ChallengeServiceImplTest {

    // Unit test 에 사용될 Mock
    @Mock
    ChallengeJpaRepository challengeJpaRepository;

    @Mock
    ParticipationJpaRepository participationJpaRepository;

    @Mock
    MemberJpaRepository userRepository;

    @Mock
    RemittanceJpaRepository remittanceJpaRepository;

    // Unit test 대상
    @InjectMocks
    ChallengeService challengeService;

}