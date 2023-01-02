package projectbuildup.saver.global.util;

import java.time.LocalDate;
import java.time.Year;
import java.util.Arrays;
import java.util.List;

public interface StringDateConverter {

    int YEAR = 0;
    int MONTH = 1;
    int DAY = 2;

    /**
     * "yyyy.mm.dd" 형식의 문자열을 LocalDate로 변환
     * @param strDate yyyy.mm.dd 형식 문자열
     * @return 날짜
     */
    public LocalDate convertToLocalDate(String strDate);
}
