package intern.rikkei.warehousesystem.dto.outbound.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request parameters for searching and filtering outbound records. (Currently no filters are implemented).")
public record OutboundSearchRequest() {
}
