package intern.rikkei.warehousesystem.dto.outbound.request;

import intern.rikkei.warehousesystem.validation.annotation.ValidShippingMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

@Schema(description = "Request body for updating an existing outbound record. All fields are optional.")
public record UpdateOutboundRequest(
        @Schema(description = "The new quantity of products to ship. Must be a positive integer.", example = "25")
        @Positive(message = "{validation.quantity.positive}")
        Integer quantity,

        @Schema(description = "The new shipping method. Allowed values: 'A', 'S', 'T', 'R'.", example = "S")
        @ValidShippingMethod
        String shippingMethod,

        @Schema(description = "The new planned shipping date. Must be today or in the future. Format: yyyy-MM-dd.", example = "2025-10-20")
        @FutureOrPresent(message = "{validation.shippingDate.futureOrPresent}")
        LocalDate shippingDate

) {
}
