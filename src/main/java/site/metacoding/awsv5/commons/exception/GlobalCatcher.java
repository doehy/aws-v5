package site.metacoding.awsv5.commons.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalCatcher {

    // 커스텀 예외 발생시
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        log.error("--- CustomException ---", ex);

        return ResponseEntity
                .status(ex.getErrorCode().getHttpStatus().value())
                .body(ErrorResponse.toErrorResponse(ex.getErrorCode()));
    }

    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<ErrorResponse> catchException(RuntimeException ex) {
        log.error("예외 핸들링", ex);
        return ResponseEntity.internalServerError()
                .body(ErrorResponse.toErrorResponse(ErrorCode.INTERNAL_SERVER));
    }

    private List<String> generateErrors(BindException ex) {
        List<String> errors = new ArrayList<>();
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();

        for (ObjectError error : allErrors) {
            errors.add(error.getDefaultMessage());
        }
        return errors;
    }

}
