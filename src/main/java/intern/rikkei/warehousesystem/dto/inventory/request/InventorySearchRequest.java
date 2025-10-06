package intern.rikkei.warehousesystem.dto.inventory.request;

import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCd;
import intern.rikkei.warehousesystem.validation.annotation.ValidEnum;
import jakarta.validation.constraints.Pattern;

public record InventorySearchRequest (
        @ValidEnum(enumClass = ProductType.class, message = "{validation.productType.invalid}", ignoreCase = true)
        String productType,

        @ValidEnum(enumClass = SupplierCd.class, message = "{validation.supplierCd.invalid}", ignoreCase = true)
        String supplierCd,

        @Pattern(regexp = "^[0-9]{9}$", message = "{validation.invoice.format}")
        String invoice
) {
}
