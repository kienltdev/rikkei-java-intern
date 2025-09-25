package intern.rikkei.warehousesystem.dto.response;

import intern.rikkei.warehousesystem.enums.ShippingMethod;

import java.time.Instant;
import java.time.LocalDate;

public record OutboundDetailResponse(
        Long id,
        Integer quantity,
        ShippingMethod shippingMethod,
        LocalDate shippingDate,
        boolean editable,
        Instant createdAt,
        Instant updatedAt,
        InboundSummaryResponse inboundSummary

) {
}
