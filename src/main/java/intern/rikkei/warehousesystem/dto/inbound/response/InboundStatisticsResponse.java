package intern.rikkei.warehousesystem.dto.inbound.response;

import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCd;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents a statistical summary for a specific group of inbounds (e.g., by product type and supplier).")
public record InboundStatisticsResponse(
        @Schema(description = "The product type for this statistical group.", example = "Aircon")
        ProductType productType,
        @Schema(description = "The supplier code for this statistical group.", example = "VN")
        SupplierCd supplierCd,
        @Schema(description = "The sum of quantities for all inbounds in this group.", example = "1500")
        Long totalQuantity,
        @Schema(description = "The total number of inbound records in this group.", example = "15")
        Long inboundCount
) {


}
