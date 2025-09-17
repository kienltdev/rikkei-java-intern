package intern.rikkei.warehousesystem.dto.response;

import intern.rikkei.warehousesystem.enums.InboundStatus;
import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCode;

import java.time.Instant;

public record InboundResponse(
        Long id,
        String invoice,
        ProductType productType,
        SupplierCode supplierCd,
        Instant receiveDate,
        InboundStatus status,
        Integer quantity,
        boolean editable,
        Instant createdAt,
        Instant updatedAt
) {
}
