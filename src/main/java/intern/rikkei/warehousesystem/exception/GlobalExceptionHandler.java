package intern.rikkei.warehousesystem.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import intern.rikkei.warehousesystem.constant.ErrorCodes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.unit.DataSize;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class GlobalExceptionHandler {
    private final MessageSource messageSource;
    private final MultipartProperties multipartProperties;
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


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<ErrorDetail> details = ex.getBindingResult().getAllErrors().stream()
                .map(error -> createErrorDetail(error))
                .collect(Collectors.toList());

        return buildValidationResponse(details, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolationException(ConstraintViolationException ex, HttpServletRequest request) {
        List<ErrorDetail> details = ex.getConstraintViolations().stream()
                .map(this::createErrorDetailFromConstraintViolation)
                .collect(Collectors.toList());

        return buildValidationResponse(details, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage("error.parameter.type.mismatch", new Object[]{ex.getName()}, locale);

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ErrorCodes.INVALID_PARAMETER_FORMAT)
                .message(message)
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDataIntegrityViolation(DataIntegrityViolationException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage("error.data.integrity.violation", null, locale);

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.CONFLICT.value())
                .code(ErrorCodes.BUSINESS_ERROR)
                .message(message)
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();

        String supportedMethodsString = Optional.ofNullable(ex.getSupportedHttpMethods())
                .map(methods -> methods.stream()
                        .map(HttpMethod::name)
                        .collect(Collectors.joining(", ")))
                .orElse("");

        Object[] args = {ex.getMethod(), supportedMethodsString};


        String message = messageSource.getMessage("error.method.not.supported", args, locale);



        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.METHOD_NOT_ALLOWED.value())
                .code(ErrorCodes.METHOD_NOT_ALLOWED)
                .message(message)
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {
        String message = "The request body is malformed or contains invalid data.";
        String specificCause = "";

        Throwable cause = ex.getCause();
        if (cause instanceof InvalidFormatException ifx) {
            String fieldName = ifx.getPath().stream()
                    .map(ref -> ref.getFieldName())
                    .collect(Collectors.joining("."));

            if (ifx.getTargetType() != null && (ifx.getTargetType().equals(Instant.class) || ifx.getTargetType().equals(java.util.Date.class))) {
                specificCause = String.format("Field '%s' must be in ISO 8601 format (e.g., 'YYYY-MM-DDTHH:mm:ssZ').", fieldName);
            } else {
                specificCause = String.format("Invalid value for field '%s'.", fieldName);
            }
            message = specificCause;
        }

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ErrorCodes.MALFORMED_REQUEST_BODY)
                .message(message)
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorResponse> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage("error.accessDenied", null, locale);

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.FORBIDDEN.value())
                .code(ErrorCodes.ACCESS_DENIED)
                .message(message)
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneralException(Exception ex, HttpServletRequest request) {
        log.error("[ERROR] {}", request.getRequestURI(), ex);
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage("error.internalServer", null, locale);

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .code(ErrorCodes.INTERNAL_SERVER_ERROR)
                .message(message)
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorResponse);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiErrorResponse> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage("error.parameter.missing", new Object[]{ex.getParameterName()}, locale);

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .code(ErrorCodes.MISSING_REQUIRED_PARAMETER)
                .message(message)
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ApiErrorResponse> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex, HttpServletRequest request) {
        log.error("[ERROR] {}", request.getRequestURI(), ex);
        Locale locale = LocaleContextHolder.getLocale();
        DataSize maxFileSize = multipartProperties.getMaxFileSize();
        String maxUploadSizeDisplay = (maxFileSize != null && !maxFileSize.isNegative())
                ? maxFileSize.toMegabytes() + "MB"
                : "N/A";
        String message = messageSource.getMessage("error.file.size.exceeded", new Object[]{maxUploadSizeDisplay}, locale);

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .timestamp(Instant.now())
                .status(HttpStatus.PAYLOAD_TOO_LARGE.value()) // Dùng mã lỗi 413 cho trường hợp này
                .code("FILE_SIZE_EXCEEDED") // Tạo một mã lỗi mới
                .message(message)
                .path(request.getRequestURI())
                .build();

        return new ResponseEntity<>(apiErrorResponse, HttpStatus.PAYLOAD_TOO_LARGE);
    }

    private ErrorDetail createErrorDetail(ObjectError error) {
        String field = (error instanceof FieldError) ? ((FieldError) error).getField() : error.getObjectName();
        String message = error.getDefaultMessage();

        return new ErrorDetail(field,  message);
    }

    private ErrorDetail createErrorDetailFromConstraintViolation(ConstraintViolation<?> violation) {
        String path = violation.getPropertyPath().toString();
        String field = path.substring(path.lastIndexOf('.') + 1);
        String message = violation.getMessage();
        return new ErrorDetail(field, message);
    }

    private ResponseEntity<ApiErrorResponse> buildValidationResponse(List<ErrorDetail> details, HttpServletRequest request) {
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


}


