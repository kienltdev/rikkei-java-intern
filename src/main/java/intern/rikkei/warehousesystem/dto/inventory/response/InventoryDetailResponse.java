package intern.rikkei.warehousesystem.dto.inventory.response;

import intern.rikkei.warehousesystem.enums.InboundStatus;
import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCd;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detailed inventory information for a single inbound record.")
public record InventoryDetailResponse(
        @Schema(description = "The ID of the inbound record.", example = "12")
        Long inbId,

        @Schema(description = "The invoice number of the inbound record.", example = "123456789")
        String invoice,
        @Schema(description = "The product type.", example = "Aircon")
        ProductType productType,
        @Schema(description = "The supplier country code.", example = "VN")
        SupplierCd supplierCd,
        @Schema(description = "The total quantity originally received in this inbound record.", example = "100")
        Integer totalQuantity,
        @Schema(description = "The quantity currently available from this specific inbound record.", example = "40")
        Long availableQuantity,
        @Schema(description = "The current outbound status of this inbound record.", example = "PARTIALLY_OUTBOUND")
        InboundStatus status
) {
}
