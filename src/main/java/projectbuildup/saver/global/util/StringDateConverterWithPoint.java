package projectbuildup.saver.global.util;

import java.time.LocalDate;
import java.time.Year;
import java.util.Arrays;
import java.util.List;

public class StringDateConverterWithPoint implements StringDateConverter {
    static final String DELIMITER = "\\.";

    @Override
    public LocalDate convertToLocalDate(String strDate){
        List<Integer> list = Arrays.stream(strDate.split(DELIMITER)).map(Integer::valueOf).toList();
        return Year.of(list.get(YEAR)).atMonth(list.get(MONTH)).atDay(DAY);
    }
}
