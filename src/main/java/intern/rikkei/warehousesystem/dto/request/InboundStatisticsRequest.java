package intern.rikkei.warehousesystem.dto.request;

import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCd;
import intern.rikkei.warehousesystem.validation.annotation.ValidEnum;
import lombok.Data;

@Data
public class InboundStatisticsRequest {

    @ValidEnum(enumClass = ProductType.class, message = "{validation.productType.invalid}", ignoreCase = true)
    private String productType;

    @ValidEnum(enumClass = SupplierCd.class, message = "{validation.supplierCd.invalid}", ignoreCase = true)
    private String supplierCd;

}
