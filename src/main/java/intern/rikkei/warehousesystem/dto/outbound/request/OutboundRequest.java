package intern.rikkei.warehousesystem.dto.outbound.request;

import intern.rikkei.warehousesystem.validation.annotation.ValidShippingMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

@Schema(description = "Request body for creating a new outbound record.")
public record OutboundRequest(
        @NotNull(message = "{validation.inboundId.required}")
        @Schema(description = "The ID of the inbound record from which to ship goods.", required = true, example = "1")
        Long inbId,

        @Schema(description = "The quantity of products to ship. Must be a positive integer and not exceed the available quantity of the inbound record.", example = "20")
        @Positive(message = "{validation.quantity.positive}")
        Integer quantity,

        @Schema(description = "The shipping method. Allowed values: 'A' (Air), 'S' (Sea), 'T' (Trail), 'R' (Road).", example = "A")
        @ValidShippingMethod
        String shippingMethod,

        @Schema(description = "The planned date for shipping. Format: yyyy-MM-dd.", example = "2025-01-15")
        LocalDate shippingDate
) {


}
