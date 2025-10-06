package intern.rikkei.warehousesystem.dto.inbound.response;

import intern.rikkei.warehousesystem.enums.ProductType;

public record InboundSummaryResponse(
        Long id,
        String invoice,
        ProductType productType,
        Long totalQuantity,
        Long quantityAvailable
) {

}
