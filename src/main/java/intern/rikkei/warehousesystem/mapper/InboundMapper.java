package intern.rikkei.warehousesystem.mapper;

import intern.rikkei.warehousesystem.dto.inbound.request.InboundImportRowDTO;
import intern.rikkei.warehousesystem.dto.inbound.request.InboundRequest;
import intern.rikkei.warehousesystem.dto.inbound.request.UpdateInboundRequest;
import intern.rikkei.warehousesystem.dto.inbound.response.InboundDetailResponse;
import intern.rikkei.warehousesystem.dto.inbound.response.InboundResponse;
import intern.rikkei.warehousesystem.entity.Inbound;
import intern.rikkei.warehousesystem.entity.Outbound;
import intern.rikkei.warehousesystem.enums.InboundStatus;
import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCd;
import org.mapstruct.*;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {InboundStatus.class, LocalDate.class, DateTimeFormatter.class}
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

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", expression = "java(InboundStatus.NOT_OUTBOUND)")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(source = "receiveDate", target = "receiveDate", qualifiedByName = "stringToLocalDate")
    Inbound fromImportDtoToEntity(InboundImportRowDTO dto);

    @Mapping(target = "editable", expression = "java(inbound.getStatus() == intern.rikkei.warehousesystem.enums.InboundStatus.NOT_OUTBOUND)")
    @Mapping(target = "quantityAvailable", source = "quantityAvailable")
    @Mapping(target = "outbounds", source = "outbounds")
    InboundDetailResponse toInboundDetailResponse(Inbound inbound, Long quantityAvailable, List<Outbound>  outbounds);

    default ProductType toProductType(String productTypeStr) {
        if (StringUtils.hasText(productTypeStr)) {
            return ProductType.valueOf(productTypeStr.trim().toUpperCase());
        }
        return null;
    }

    default SupplierCd toSupplierCode(String supplierCdStr) {
        if (StringUtils.hasText(supplierCdStr)) {
            return intern.rikkei.warehousesystem.enums.SupplierCd.fromCode(supplierCdStr.trim().toUpperCase());
        }
        return null;
    }

    @Named("stringToLocalDate")
    default LocalDate stringToLocalDate(String date) {
        if (StringUtils.hasText(date)) {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
        return null;
    }
}