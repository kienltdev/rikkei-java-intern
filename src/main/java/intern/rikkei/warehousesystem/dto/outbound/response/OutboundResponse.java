package intern.rikkei.warehousesystem.dto.outbound.response;

import intern.rikkei.warehousesystem.enums.ShippingMethod;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.time.LocalDate;

@Schema(description = "Summary information of an outbound record.")
public record OutboundResponse(
        @Schema(description = "Unique identifier of the outbound record.", example = "1")
        Long id,
        @Schema(description = "The ID of the associated inbound record.", example = "12")
        Long inbId,
        @Schema(description = "The quantity of products shipped.", example = "20")
        Integer quantity,

        @Schema(description = "The shipping method used.", example = "A")
        ShippingMethod shippingMethod,
        @Schema(description = "The planned shipping date.", example = "2025-01-15")
        LocalDate shippingDate,
        @Schema(description = "Indicates if the record can be edited or deleted (true if shipping date has not passed).", example = "true")
        boolean editable,
        @Schema(description = "Timestamp of creation.")
        Instant createdAt,
        @Schema(description = "Timestamp of last update.")
        Instant updatedAt
) {
}
