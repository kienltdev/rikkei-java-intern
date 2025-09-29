package intern.rikkei.warehousesystem.dto.request;

import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCd;
import intern.rikkei.warehousesystem.validation.annotation.ValidEnum;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record InventoryListRequest(
        @Positive(message = "{validation.inboundId.positive}")
        Long inbId,

        @Pattern(regexp = "^[0-9]{9}$", message = "{validation.invoice.format}")
        String invoice,

        @ValidEnum(enumClass = ProductType.class, message = "{validation.productType.invalid}", ignoreCase = true)
        String productType,

        @ValidEnum(enumClass = SupplierCd.class, message = "{validation.supplierCd.invalid}", ignoreCase = true)
        String supplierCd

) {
}
