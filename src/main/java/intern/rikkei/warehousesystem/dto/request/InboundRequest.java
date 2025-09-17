package intern.rikkei.warehousesystem.dto.request;

import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCode;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Instant;

public record InboundRequest(
        @NotBlank(message = "{validation.invoice.required}")
        @Size(max = 9, message = "{validation.invoice.length}")
        String invoice,

        @NotNull(message = "{validation.productType.required}")
        ProductType productType,

        @NotNull(message = "{validation.supplierCd.required}")
        SupplierCode supplierCd,

        Instant receiveDate,

        @NotNull(message = "{validation.quantity.required}")
        @Min(value = 1, message = "{validation.quantity.min}")
        Integer quantity
) {
}
