package intern.rikkei.warehousesystem.exception;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;

@Data
@Builder
public class ApiErrorResponse {
    private Instant timestamp;
    private String path;
    private ErrorPayload error;
}