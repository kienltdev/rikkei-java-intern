package intern.rikkei.warehousesystem.dto.request;

import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCode;
import intern.rikkei.warehousesystem.validation.annotation.ValidEnum;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record UpdateInboundRequest(

        @Pattern(
                regexp = "^[0-9]{9}$",
                message = "{validation.invoice.format}"
        )
        String invoice,


        @ValidEnum(enumClass = ProductType.class,
                message = "{validation.productType.invalid}",
                ignoreCase = true)
        String productType,


        @ValidEnum(enumClass = SupplierCode.class,
                message = "{validation.supplierCd.invalid}",
                ignoreCase = true)
        String supplierCd,

        LocalDate receiveDate,

        @PositiveOrZero(message = "{validation.quantity.positiveOrZero}")
        Integer quantity

) {
}
