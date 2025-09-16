package intern.rikkei.warehousesystem.exception;

import intern.rikkei.warehousesystem.constant.ErrorCodes;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessException(BusinessException ex, HttpServletRequest request) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .timestamp(Instant.now())
                .status(ex.getStatus().value())
                .code(ex.getCode())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(ex.getStatus()).body(apiErrorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneralException(Exception ex, HttpServletRequest request) {
        String message = messageSource.getMessage("error.internalServer", null, request.getLocale());

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .code(ErrorCodes.INTERNAL_SERVER_ERROR)
                .message(message)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ErrorDetail> details = ex.getBindingResult().getAllErrors().stream()
                .map(error -> createErrorDetail(error))
                .collect(Collectors.toList());

        String message = messageSource.getMessage("error.validation", null, request.getLocale());

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ErrorCodes.VALIDATION_ERROR)
                .message(message)
                .path(request.getRequestURI())
                .validationErrors(details)
                .build();

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    private ErrorDetail createErrorDetail(ObjectError error) {
        String field = (error instanceof FieldError) ? ((FieldError) error).getField() : error.getObjectName();
        String message = error.getDefaultMessage();

        return new ErrorDetail(field,  message);
    }


}


