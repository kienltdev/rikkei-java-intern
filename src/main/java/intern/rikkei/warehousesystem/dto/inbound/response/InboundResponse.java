package intern.rikkei.warehousesystem.dto.inbound.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import intern.rikkei.warehousesystem.enums.InboundStatus;
import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCd;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.time.LocalDate;

@Schema(description = "Summary information of an inbound record")
public record InboundResponse(
        @Schema(description = "Unique identifier of the inbound record", example = "1")
        Long id,
        @Schema(description = "Invoice number", example = "123456789")
        String invoice,
        @Schema(description = "Product type", implementation = ProductType.class, example = "Aircon")
        ProductType productType,
        @Schema(description = "Supplier country code", implementation = SupplierCd.class, example = "VN")
        SupplierCd supplierCd,
        @Schema(description = "Date goods were received (dd/MM/yyyy)", example = "25/12/2024")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate receiveDate,
        @Schema(description = "Current status of the inbound record", implementation = InboundStatus.class, example = "0")
        InboundStatus status,
        @Schema(description = "Total quantity of products received", example = "100")
        Integer quantity,
        @Schema(description = "Indicates if the record can be edited or deleted (true if status is NOT_OUTBOUND)", example = "true")
        boolean editable,
        @Schema(description = "Timestamp of creation")
        Instant createdAt,
        @Schema(description = "Timestamp of last update")
        Instant updatedAt
) {
}
