package intern.rikkei.warehousesystem.dto.response;

import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCd;

public record InboundStatisticsResponse(
        ProductType productType,
        SupplierCd supplierCd,
        Long totalQuantity,
        Long totalInBoundsOrder) {


}
