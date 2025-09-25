package intern.rikkei.warehousesystem.dto.response;

import intern.rikkei.warehousesystem.enums.ProductType;

public record InboundSummaryResponse(
        Long id,
        String invoice,
        ProductType productType,
        Integer totalQuantity,
        Integer quantityAvailable
) {

}
