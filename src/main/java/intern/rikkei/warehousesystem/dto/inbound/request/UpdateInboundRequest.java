package intern.rikkei.warehousesystem.dto.inbound.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCd;
import intern.rikkei.warehousesystem.validation.annotation.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

@Schema(description = "Request body for updating an existing inbound record. All fields are optional.")
public record UpdateInboundRequest(

        @Schema(description = "New invoice number. Must be exactly 9 digits.", example = "987654321")
        @Pattern(
                regexp = "^[0-9]{9}$",
                message = "{validation.invoice.format}"
        )
        String invoice,


        @Schema(description = "New product type. Allowed values: 'Aircon', 'Spare_part'. Case-insensitive.", example = "Spare_part")
        @ValidEnum(enumClass = ProductType.class,
                message = "{validation.productType.invalid}",
                ignoreCase = true)
        String productType,


        @Schema(description = "New supplier's country code. E.g., 'VN', 'TH'. Case-insensitive.", example = "TH")
        @ValidEnum(enumClass = SupplierCd.class,
                message = "{validation.supplierCd.invalid}",
                ignoreCase = true)
        String supplierCd,

        @Schema(description = "New receive date. Format: dd/MM/yyyy. Cannot be in the future.", example = "26/12/2024")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        @PastOrPresent(message = "{validation.receiveDate.pastOrPresent}")
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
