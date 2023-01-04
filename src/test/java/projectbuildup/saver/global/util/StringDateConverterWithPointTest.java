package projectbuildup.saver.global.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Year;

class StringDateConverterWithPointTest {
    @Test
    @DisplayName("yyyy.mm.dd 형식의 문자열을 LocalDate 형식으로 변환한다.")
    void yyyymmdd_포인트_형식의_문자열을_LocalDate_형식으로_변환한다() {
        // given
        String strDate = "2023.01.02";

        // when
        StringDateConverter converter = new StringDateConverterWithPoint();

        // then
        LocalDate result = converter.convertToLocalDate(strDate);
        LocalDate expected = Year.of(2023).atMonth(1).atDay(2);
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("yyyy-mm-dd 형식의 문자열을 LocalDate 형식으로 변환한다.")
    void yyyymmdd_dash_형식의_문자열을_LocalDate_형식으로_변환한다() {
        // given
        String strDate = "2023-01-02";

        // when
        StringDateConverter converter = new StringDateConverterWithDash();

        // then
        LocalDate result = converter.convertToLocalDate(strDate);
        LocalDate expected = Year.of(2023).atMonth(1).atDay(2);
        Assertions.assertThat(result).isEqualTo(expected);
    }
}