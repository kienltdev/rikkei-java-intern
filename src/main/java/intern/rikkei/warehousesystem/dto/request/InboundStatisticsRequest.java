package intern.rikkei.warehousesystem.dto.request;

import intern.rikkei.warehousesystem.constant.ApiConstants;
import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCode;
import intern.rikkei.warehousesystem.validation.annotation.ValidEnum;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class InboundStatisticsRequest {
    @Min(value =0, message = "{validation.page.min}")
    private int page = 0;
    private int size = ApiConstants.DEFAULT_PAGE_SIZE;

    @ValidEnum(enumClass = ProductType.class, message = "{validation.productType.invalid}", ignoreCase = true)
    private String productType;

    @ValidEnum(enumClass = SupplierCode.class, message = "{validation.supplierCd.invalid}", ignoreCase = true)
    private String supplierCd;

}
