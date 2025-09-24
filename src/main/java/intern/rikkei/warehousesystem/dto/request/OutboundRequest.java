package intern.rikkei.warehousesystem.dto.request;

import intern.rikkei.warehousesystem.enums.ShippingMethod;
import intern.rikkei.warehousesystem.validation.annotation.ValidEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record OutboundRequest(
        @NotNull(message = "{validation.inboundId.required}")
        Long inbId,

        @NotNull(message = "{validation.quantity.required")
        @Positive(message = "{validation.quantity.positive}")
        Integer quantity,

        @NotNull(message = "{validation.shippingMethod.required")
        @ValidEnum(enumClass = ShippingMethod.class, message = "{validation.shippMethod.invalid", ignoreCase = true)
        String shippingMethod,

        LocalDate shippingDate
) {


}
