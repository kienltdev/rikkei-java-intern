package intern.rikkei.warehousesystem.dto.response;

import intern.rikkei.warehousesystem.enums.InboundStatus;
import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCd;

import java.time.Instant;
import java.time.LocalDate;

public record InboundResponse(
        Long id,
        String invoice,
        ProductType productType,
        SupplierCd supplierCd,
        LocalDate receiveDate,
        InboundStatus status,
        Integer quantity,
        boolean editable,
        Instant createdAt,
        Instant updatedAt
) {
}
