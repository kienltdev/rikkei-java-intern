package intern.rikkei.warehousesystem.dto.inbound.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCd;
import intern.rikkei.warehousesystem.validation.annotation.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record InboundRequest(
        @Schema(description = "Invoice number, must be exactly 9 digits.", example = "123456789")
        @NotBlank(message = "{validation.invoice.required}")
        @Pattern(regexp = "^[0-9]{9}$",
                message = "{validation.invoice.format}")
        String invoice,

        @Schema(description = "Type of the product. Allowed values: 'Aircon', 'Spare_part'. Case-insensitive.", example = "Aircon")
        @NotBlank(message = "{validation.productType.required}")
        @ValidEnum(enumClass = ProductType.class, message = "{validation.productType.invalid}", ignoreCase = true)
        String  productType,

        @Schema(description = "Supplier's country code. E.g., 'VN' for Vietnam, 'TH' for Thailand. Case-insensitive.", example = "VN")
        @NotBlank(message = "{validation.supplierCd.required}")
        @ValidEnum(enumClass = SupplierCd.class, message = "{validation.supplierCd.invalid}", ignoreCase = true)
        String  supplierCd,

        @Schema(description = "Date when the goods were received. Format: dd/MM/yyyy. Cannot be in the future.", example = "25/12/2024")
        @PastOrPresent(message = "{validation.receiveDate.pastOrPresent}")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate receiveDate,

        @Schema(
                description = "Quantity of the product received. Must be between 1 and 100,000.",
                example = "100",
                minimum = "1",
                maximum = "1000000"
        )
        @Positive(message = "{validation.quantity.positive}")
        @Max(value = 1000000, message = "{validation.quantity.max}")
        Integer quantity
) {
}
