package projectbuildup.saver.domain.challenge.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import projectbuildup.saver.domain.challenge.entity.Challenge;
import projectbuildup.saver.domain.dto.req.CreateChallengeRequestDto;
import projectbuildup.saver.global.config.JpaAuditingConfig;
import projectbuildup.saver.global.constant.ExampleValue;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@Import(JpaAuditingConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ChallengeRepositoryTest {

    @Autowired
    ChallengeJpaRepository challengeJpaRepository;

    @Test
    @DisplayName("DB에 챌린지를 저장하고 조회한다.")
    public void DB에_챌린지를_저장하고_조회한다() {
        // given
        CreateChallengeRequestDto dto = CreateChallengeRequestDto.builder()
                .startDate(ExampleValue.challenge.START_DATE)
                .endDate(ExampleValue.challenge.END_DATE)
                .mainTitle(ExampleValue.challenge.MAIN_TITLE)
                .subTitle(ExampleValue.challenge.SUBTITLE)
                .content(ExampleValue.challenge.CONTENT)
                .savingAmount(ExampleValue.challenge.AMOUNT)
                .build();

        // when
        Challenge created = challengeJpaRepository.save(dto.toEntity());
        Challenge found = challengeJpaRepository.findById(created.getId()).orElse(null);

        // then
        assertThat(dto.getMainTitle()).isEqualTo(found.getMainTitle());
    }
}
