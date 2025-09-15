package intern.rikkei.warehousesystem.exception;

import lombok.Builder;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Builder
public record ApiErrorResponse (
        Instant timestamp,
        int status,
        String code,
        String message,
        String path,
        List<ErrorDetail> errors
){
    public ApiErrorResponse {
        if (errors == null) {
            errors = Collections.emptyList();
        }
    }
}