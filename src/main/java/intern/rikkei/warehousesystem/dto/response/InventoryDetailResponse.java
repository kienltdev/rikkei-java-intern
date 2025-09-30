package intern.rikkei.warehousesystem.dto.response;

import intern.rikkei.warehousesystem.enums.InboundStatus;
import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCd;


public record InventoryDetailResponse(
        Long inbId,
        String invoice,
        ProductType productType,
        SupplierCd supplierCd,
        Integer totalQuantity,
        Long availableQuantity,
        InboundStatus status
) {
}
