package intern.rikkei.warehousesystem.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import intern.rikkei.warehousesystem.common.constant.ErrorCodes;
import intern.rikkei.warehousesystem.common.constant.MessageConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.ArrayList;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneralException(Exception exception, HttpServletRequest request) {
        ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .code(ErrorCodes.INTERNAL_SERVER_ERROR)
                .message(MessageConstants.INTERNAL_SERVER_ERROR)
                .path(request.getRequestURI())
                .details(new ArrayList<>())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
