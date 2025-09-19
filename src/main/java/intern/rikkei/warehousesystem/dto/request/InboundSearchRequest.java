package intern.rikkei.warehousesystem.dto.request;

import intern.rikkei.warehousesystem.constant.ApiConstants;
import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCode;
import intern.rikkei.warehousesystem.validation.annotation.ValidEnum;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class InboundSearchRequest {

    @Min(value = 0, message = "Page index must not be less than zero")
    private int page = 0; // Gán giá trị mặc định

    @Min(value = 1, message = "Page size must be at least one")
    private int size = ApiConstants.DEFAULT_PAGE_SIZE; // Gán giá trị mặc định

    @ValidEnum(enumClass = ProductType.class, message = "{validation.productType.invalid}", ignoreCase = true)
    private String productType;

    @ValidEnum(enumClass = SupplierCode.class, message = "{validation.supplierCd.invalid}", ignoreCase = true)
    private String supplierCd;
}
