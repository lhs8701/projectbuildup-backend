package projectbuildup.saver.domain.dto.req;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PhoneAuthDto {
    private String phoneNumber;
    private String code;
}
