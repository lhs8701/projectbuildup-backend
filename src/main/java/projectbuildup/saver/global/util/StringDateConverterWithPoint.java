package projectbuildup.saver.global.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Year;
import java.util.Arrays;
import java.util.List;

@Component
@Primary
public class StringDateConverterWithPoint implements StringDateConverter {
    static final String DELIMITER = "\\.";

    @Override
    public LocalDate convertToLocalDate(String strDate){
        List<Integer> list = Arrays.stream(strDate.split(DELIMITER)).map(Integer::valueOf).toList();
        return Year.of(list.get(YEAR)).atMonth(list.get(MONTH)).atDay(DAY);
    }
}
