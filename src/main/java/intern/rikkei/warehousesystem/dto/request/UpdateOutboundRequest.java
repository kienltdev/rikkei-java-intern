package intern.rikkei.warehousesystem.dto.request;

import intern.rikkei.warehousesystem.validation.annotation.ValidShippingMethod;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record UpdateOutboundRequest(
        @Positive(message = "{validation.quantity.positive}")
        Integer quantity,

        @ValidShippingMethod
        String shippingMethod,

        @FutureOrPresent(message = "{validation.shippingDate.futureOrPresent}")
        LocalDate shippingDate

) {
}
