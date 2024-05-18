package site.metacoding.awsv5.commons.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@EqualsAndHashCode
public class ErrorResponse {
    private final Integer status;
    private final String message;
    private final List<String> errors;

    public ErrorResponse(HttpStatus status, String message, String error) {
        this.status = status.value();
        this.message = message;
        this.errors = List.of(error);
    }

    public ErrorResponse(HttpStatus status, String message, List<String> error) {
        this.status = status.value();
        this.message = message;
        this.errors = error;
    }

    public static ErrorResponse toErrorResponse(ErrorCode errorCode){
        return new ErrorResponse(
                errorCode.getHttpStatus(),
                errorCode.name(),
                errorCode.getDetail());
    }
}
