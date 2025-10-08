package intern.rikkei.warehousesystem.dto.inbound.response;

import intern.rikkei.warehousesystem.enums.ProductType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "A brief summary of an inbound record, often used when nested inside other responses.")
public record InboundSummaryResponse(
        @Schema(description = "Unique identifier of the inbound record", example = "1")
        Long id,
        @Schema(description = "Invoice number", example = "123456789")
        String invoice,
        @Schema(description = "Product type", example = "Aircon")
        ProductType productType,
        @Schema(description = "Total quantity of products in this inbound record", example = "100")
        Long totalQuantity,
        @Schema(description = "The quantity that is currently available for outbound shipment", example = "70")
        Long quantityAvailable
) {

}
