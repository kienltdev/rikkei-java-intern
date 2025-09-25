package intern.rikkei.warehousesystem.dto.request;

import intern.rikkei.warehousesystem.validation.annotation.ValidShippingMethod;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record OutboundRequest(
        @NotNull(message = "{validation.inboundId.required}")
        Long inbId,

        @Positive(message = "{validation.quantity.positive}")
        Integer quantity,

        @ValidShippingMethod
        String shippingMethod,

        LocalDate shippingDate
) {


}
