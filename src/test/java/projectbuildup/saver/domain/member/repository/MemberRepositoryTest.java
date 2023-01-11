package projectbuildup.saver.domain.member.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import projectbuildup.saver.domain.auth.basic.dto.SignupRequestDto;
import projectbuildup.saver.domain.user.entity.Member;
import projectbuildup.saver.domain.user.repository.MemberJpaRepository;
import projectbuildup.saver.global.config.JpaAuditingConfig;
import projectbuildup.saver.global.constant.ExampleValue;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
@Import(JpaAuditingConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    @DisplayName("DB에 회원을 저장하고 조회한다.")
    public void DB에_회원을_저장하고_조회한다() {
        // given
        SignupRequestDto memberCreationRequestDto = SignupRequestDto.builder()
                .idToken(ExampleValue.Member.ID_TOKEN)
                .password(ExampleValue.Member.PASSWORD)
                .nickname(ExampleValue.Member.NICKNAME)
                .phoneNumber(ExampleValue.Member.MOBILE)
                .build();

        // when
        Member createdMember = memberJpaRepository.save(memberCreationRequestDto.toEntity("encodedPassword"));
        Member foundMember = memberJpaRepository.findById(createdMember.getId()).orElse(null);

        // then
        assertThat(foundMember.getIdToken()).isEqualTo(memberCreationRequestDto.getIdToken());
    }


    @Test
    @DisplayName("DB에서 회원을 삭제한다.")
    public void DB에서_회원을_삭제한다() {
        // given
        SignupRequestDto memberCreationRequestDto = SignupRequestDto.builder()
                .idToken(ExampleValue.Member.ID_TOKEN)
                .password(ExampleValue.Member.PASSWORD)
                .nickname(ExampleValue.Member.NICKNAME)
                .phoneNumber(ExampleValue.Member.MOBILE)
                .build();


        Member createdMember = memberJpaRepository.save(memberCreationRequestDto.toEntity("encodedPassword"));

        // when
        memberJpaRepository.deleteById(createdMember.getId());

        // then
        Optional<Member> found = memberJpaRepository.findById(createdMember.getId());
        assertThat(found.isEmpty()).isEqualTo(true);
    }

    @Test
    @DisplayName("DB에 회원을 저장하고 생성 날짜를 조회한다.")
    public void JPA_DB로_회원을_저장하고_생성_날짜를_조회한다() {
        // given
        SignupRequestDto memberCreationRequestDto = SignupRequestDto.builder()
                .idToken(ExampleValue.Member.ID_TOKEN)
                .password(ExampleValue.Member.PASSWORD)
                .nickname(ExampleValue.Member.NICKNAME)
                .phoneNumber(ExampleValue.Member.MOBILE)
                .build();

        LocalDate today = LocalDate.now();

        // when
        Member createdMember = memberJpaRepository.save(memberCreationRequestDto.toEntity("encodedPassword"));
        Member foundMember = memberJpaRepository.findById(createdMember.getId()).orElse(null);
        LocalDate createdDate = foundMember.getCreatedTime().toLocalDate();

        // then
        assertThat(createdDate).isEqualTo(today);
    }
}
