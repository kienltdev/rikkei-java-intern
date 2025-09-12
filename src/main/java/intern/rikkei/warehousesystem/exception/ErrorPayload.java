package intern.rikkei.warehousesystem.exception;

import lombok.Builder;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@Builder
public class ErrorPayload {
    private int status;
    private String code;
    private String message;
    @Builder.Default
    private List<ErrorDetail> details = Collections.emptyList();;
}