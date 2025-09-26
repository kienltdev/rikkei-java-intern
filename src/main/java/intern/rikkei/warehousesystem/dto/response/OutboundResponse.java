package intern.rikkei.warehousesystem.dto.response;

import intern.rikkei.warehousesystem.enums.ShippingMethod;

import java.time.Instant;
import java.time.LocalDate;

public record OutboundResponse(
        Long id,
        Long inbId,
        Integer quantity,
        ShippingMethod shippingMethod,
        LocalDate shippingDate,
        boolean editable,
        Instant createdAt,
        Instant updatedAt
) {
}
