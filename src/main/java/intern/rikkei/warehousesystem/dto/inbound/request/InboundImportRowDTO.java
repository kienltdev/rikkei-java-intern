package intern.rikkei.warehousesystem.dto.inbound.request;

import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCd;
import intern.rikkei.warehousesystem.validation.annotation.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "Represents a single data row parsed from an import file (CSV/Excel) for validation purposes.")
public record InboundImportRowDTO(
        @Schema(description = "Supplier's country code from the import file.", example = "VN")
        @NotBlank(message = "{validation.supplierCd.required}")
        @ValidEnum(enumClass = SupplierCd.class, message = "{validation.supplierCd.invalid}", ignoreCase = true)
        String supplierCd,

        @Schema(description = "Invoice number from the import file.", example = "123456789")
        @NotBlank(message = "{validation.invoice.required}")
        @Pattern(regexp = "^[0-9]{9}$", message = "{validation.invoice.format}")
        String invoice,

        @Schema(description = "Product type from the import file.", example = "Aircon")
        @NotBlank(message = "{validation.productType.required}")
        @ValidEnum(enumClass = ProductType.class, message = "{validation.productType.invalid}", ignoreCase = true)
        String productType,

        @Schema(description = "Product quantity from the import file.", example = "100")
        @PositiveOrZero(message = "{validation.quantity.positiveOrZero}")
        Integer quantity,

        @Schema(description = "Receive date as a string from the import file. Expected format: dd/MM/yyyy", example = "25/12/2024")
        @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[012])/\\d{4}$", message = "Invalid date format. Please use dd/MM/yyyy")
        String receiveDate
) {
}