package intern.rikkei.warehousesystem.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCd;
import intern.rikkei.warehousesystem.validation.annotation.ValidEnum;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

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


        @ValidEnum(enumClass = SupplierCd.class,
                message = "{validation.supplierCd.invalid}",
                ignoreCase = true)
        String supplierCd,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        @PastOrPresent(message = "{validation.receiveDate.pastOrPresent}")
        LocalDate receiveDate,

        @Positive(message = "{validation.quantity.positive}")
        Integer quantity

) {
}
