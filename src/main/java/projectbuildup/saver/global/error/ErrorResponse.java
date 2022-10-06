package projectbuildup.saver.global.error;


import lombok.Getter;

@Getter
public class ErrorResponse {
    private final String message;
    private final int code;

    public ErrorResponse(final ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public static ErrorResponse of(final ErrorCode code) {
        return new ErrorResponse(code);
    }
}
