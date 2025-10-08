package intern.rikkei.warehousesystem.dto.inbound.request;

import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCd;
import intern.rikkei.warehousesystem.validation.annotation.ValidEnum;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;

@Data
public class InboundStatisticsRequest {

    @Parameter(description = "Filter by product type (e.g., 'Aircon', 'Spare_part'). Case-insensitive.", example = "Aircon")
    @ValidEnum(enumClass = ProductType.class, message = "{validation.productType.invalid}", ignoreCase = true)
    private String productType;

    @Parameter(description = "Filter by supplier country code (e.g., 'VN', 'TH'). Case-insensitive.", example = "VN")
    @ValidEnum(enumClass = SupplierCd.class, message = "{validation.supplierCd.invalid}", ignoreCase = true)
    private String supplierCd;

}
