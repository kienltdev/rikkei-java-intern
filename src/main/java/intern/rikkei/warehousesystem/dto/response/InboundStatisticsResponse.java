package intern.rikkei.warehousesystem.dto.response;

import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCode;

public record InboundStatisticsResponse(
        ProductType productType,
        SupplierCode supplierCode,
        Long totalQuantity,
        Long totalInBoundsOrder) {


}
