package intern.rikkei.warehousesystem.dto.outbound.response;

import intern.rikkei.warehousesystem.enums.ShippingMethod;

import java.time.Instant;
import java.time.LocalDate;

public record OutboundSummaryResponse(
        Long id,
        Integer quantity,
        ShippingMethod shippingMethod,
        LocalDate shippingDate,
        Instant createdAt,
        Instant updatedAt
) {
}
