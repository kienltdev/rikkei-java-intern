package intern.rikkei.warehousesystem.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrorResponse {
    private Instant timestamp;
    private int status;
    private String error;
    private String code;
    private String message;
    private String path;
    List<ErrorDetail> details;
}
