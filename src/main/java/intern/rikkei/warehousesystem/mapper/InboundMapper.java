package intern.rikkei.warehousesystem.mapper;

import intern.rikkei.warehousesystem.dto.request.InboundRequest;
import intern.rikkei.warehousesystem.dto.request.UpdateInboundRequest;
import intern.rikkei.warehousesystem.dto.response.InboundResponse;
import intern.rikkei.warehousesystem.entity.Inbound;
import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.util.StringUtils;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface InboundMapper {

    Inbound toInbound(InboundRequest request);

    @Mapping(target = "editable", expression = "java(inbound.getStatus() == intern.rikkei.warehousesystem.enums.InboundStatus.NOT_OUTBOUND)")
    InboundResponse toInboundResponse(Inbound inbound);



    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateInboundFromRequest(UpdateInboundRequest request, @MappingTarget Inbound inbound);

    default ProductType toProductType(String productTypeStr) {
        if (StringUtils.hasText(productTypeStr)) {
            return ProductType.valueOf(productTypeStr.trim().toUpperCase());
        }
        return null;
    }

    default SupplierCode toSupplierCode(String supplierCdStr) {
        if (StringUtils.hasText(supplierCdStr)) {
            return SupplierCode.fromCode(supplierCdStr.trim().toUpperCase());
        }
        return null;
    }
}