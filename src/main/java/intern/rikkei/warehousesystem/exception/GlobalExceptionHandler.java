package intern.rikkei.warehousesystem.exception;

import intern.rikkei.warehousesystem.constant.ErrorCodes;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessException(BusinessException ex, HttpServletRequest request){
        ErrorPayload error = ErrorPayload.builder()
                .status(ex.getStatus().value())
                .code(ex.getCode())
                .message(ex.getMessage())
                .build();

        ApiErrorResponse apiErrorResponse =  ApiErrorResponse.builder()
                .timestamp(Instant.now())
                .path(request.getRequestURI())
                .error(error)
                .build();
        return ResponseEntity.status(ex.getStatus().value())
                .body(apiErrorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneralException(Exception exception, HttpServletRequest request) {
        String message = messageSource.getMessage("error.internalServer", null, request.getLocale());
        ErrorPayload errorPayload = ErrorPayload.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .code(ErrorCodes.INTERNAL_SERVER_ERROR)
                .message(message)
                .build();


        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .timestamp(Instant.now())
                .path(request.getRequestURI())
                .error(errorPayload)
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ErrorDetail> details = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName =  (error instanceof FieldError) ? ((FieldError) error).getField() : error.getObjectName();
                    String errorMessage = error.getDefaultMessage();
                    return new ErrorDetail(fieldName, errorMessage);
                })
                .collect(Collectors.toList());

        String message = messageSource.getMessage("error.validation", null, request.getLocale());

        ErrorPayload error = ErrorPayload.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ErrorCodes.VALIDATION_ERROR)
                .message(message)
                .details(details)
                .build();

        ApiErrorResponse apiErrorResponse =  ApiErrorResponse.builder()
                .timestamp(Instant.now())
                .path(request.getRequestURI())
                .error(error)
                .build();

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }


}
