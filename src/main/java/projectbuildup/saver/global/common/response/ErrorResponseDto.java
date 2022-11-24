package projectbuildup.saver.global.common.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import projectbuildup.saver.global.error.ErrorCode;

@Getter
@NoArgsConstructor
public class ErrorResponseDto {
    int code;
    String message;
    int statusCode;

    public ErrorResponseDto(ErrorCode errorCode){
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.statusCode = errorCode.getStatusCode().value();
    }
}
