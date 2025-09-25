package intern.rikkei.warehousesystem.mapper;

import intern.rikkei.warehousesystem.dto.response.InboundSummaryResponse;
import intern.rikkei.warehousesystem.dto.response.OutboundDetailResponse;
import intern.rikkei.warehousesystem.dto.response.OutboundResponse;
import intern.rikkei.warehousesystem.entity.Inbound;
import intern.rikkei.warehousesystem.entity.Outbound;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;

@Mapper(componentModel = "spring", imports = {LocalDate.class})
public interface OutboundMapper {
    @Mapping(source = "inbound.id", target = "inbId")
    @Mapping(source = "shippingDate", target = "editable", qualifiedByName = "isEditable")
    OutboundResponse toOutboundResponse(Outbound outbound);

    @Mapping(source = "outbound.inbound", target = "inboundSummary")
    @Mapping(source = "outbound.shippingDate", target = "editable", qualifiedByName = "isEditable")
    OutboundDetailResponse  toOutboundDetailResponse(Outbound outbound, @Context Integer quantityAvailable);


    @Mapping(target = "totalQuantity", source = "quantity")
    @Mapping(target = "quantityAvailable", expression = "java(quantityAvailable)")
    InboundSummaryResponse  toInboundSummaryResponse(Inbound inbound, @Context Integer quantityAvailable);

    @Named("isEditable")
    default boolean isEditable(LocalDate shippingDate) {
        return shippingDate == null || shippingDate.isBefore(LocalDate.now());
    }

}
