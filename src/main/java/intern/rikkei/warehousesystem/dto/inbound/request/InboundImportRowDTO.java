package intern.rikkei.warehousesystem.dto.inbound.request;

import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCd;
import intern.rikkei.warehousesystem.validation.annotation.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;


public record InboundImportRowDTO(
        @NotBlank(message = "{validation.supplierCd.required}")
        @ValidEnum(enumClass = SupplierCd.class, message = "{validation.supplierCd.invalid}", ignoreCase = true)
        String supplierCd,

        @NotBlank(message = "{validation.invoice.required}")
        @Pattern(regexp = "^[0-9]{9}$", message = "{validation.invoice.format}")
        String invoice,

        @NotBlank(message = "{validation.productType.required}")
        @ValidEnum(enumClass = ProductType.class, message = "{validation.productType.invalid}", ignoreCase = true)
        String productType,

        @PositiveOrZero(message = "{validation.quantity.positiveOrZero}")
        Integer quantity,

        // Định dạng ngày tháng dd/MM/yyyy
        @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/\\d{4}$", message = "Invalid date format. Please use dd/MM/yyyy")
        String receiveDate
) {
}