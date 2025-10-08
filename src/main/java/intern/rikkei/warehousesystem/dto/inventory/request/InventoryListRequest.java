package intern.rikkei.warehousesystem.dto.inventory.request;

import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCd;
import intern.rikkei.warehousesystem.validation.annotation.ValidEnum;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record InventoryListRequest(
        @Parameter(description = "Filter by a specific inbound ID.", example = "12")
        @Positive(message = "{validation.inboundId.positive}")
        Long inbId,
        @Parameter(description = "Filter by invoice number (supports partial matching).", example = "456")
        @Pattern(regexp = "^[0-9]{9}$", message = "{validation.invoice.format}")
        String invoice,
        @Parameter(description = "Filter by product type (e.g., 'Aircon', 'Spare_part'). Case-insensitive.", example = "Spare_part")
        @ValidEnum(enumClass = ProductType.class, message = "{validation.productType.invalid}", ignoreCase = true)
        String productType,
        @Parameter(description = "Filter by supplier country code (e.g., 'VN', 'TH'). Case-insensitive.", example = "TH")
        @ValidEnum(enumClass = SupplierCd.class, message = "{validation.supplierCd.invalid}", ignoreCase = true)
        String supplierCd

) {
}
