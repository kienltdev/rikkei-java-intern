package intern.rikkei.warehousesystem.dto.inbound.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import intern.rikkei.warehousesystem.dto.outbound.response.OutboundSummaryResponse;
import intern.rikkei.warehousesystem.enums.InboundStatus;
import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCd;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public record InboundDetailResponse(
        Long id,
        String invoice,
        ProductType productType,
        SupplierCd supplierCd,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate receiveDate,
        InboundStatus status,
        Integer quantity,
        Integer quantityAvailable,
        Boolean editable,
        Instant createdAt,
        Instant updatedAt,
        List<OutboundSummaryResponse> outbounds
) {
}
