package intern.rikkei.warehousesystem.mapper;

import intern.rikkei.warehousesystem.dto.outbound.request.UpdateOutboundRequest;
import intern.rikkei.warehousesystem.dto.inbound.response.InboundSummaryResponse;
import intern.rikkei.warehousesystem.dto.outbound.response.OutboundDetailResponse;
import intern.rikkei.warehousesystem.dto.outbound.response.OutboundResponse;
import intern.rikkei.warehousesystem.dto.outbound.response.OutboundSummaryResponse;
import intern.rikkei.warehousesystem.entity.Inbound;
import intern.rikkei.warehousesystem.entity.Outbound;
import intern.rikkei.warehousesystem.enums.ShippingMethod;
import org.mapstruct.*;

import java.time.LocalDate;
import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {LocalDate.class})
public interface OutboundMapper {
    @Mapping(source = "inbound.id", target = "inbId")
    @Mapping(source = "shippingDate", target = "editable", qualifiedByName = "isEditable")
    OutboundResponse toOutboundResponse(Outbound outbound);

    @Mapping(source = "outbound.inbound", target = "inboundSummary")
    @Mapping(source = "outbound.shippingDate", target = "editable", qualifiedByName = "isEditable")
    OutboundDetailResponse  toOutboundDetailResponse(Outbound outbound, @Context Long quantityAvailable);


    @Mapping(target = "totalQuantity", source = "quantity")
    @Mapping(target = "quantityAvailable", expression = "java(quantityAvailable)")
    InboundSummaryResponse  toInboundSummaryResponse(Inbound inbound, @Context Long quantityAvailable);

    OutboundSummaryResponse toOutboundSummaryResponse(Outbound outbound);
    List<OutboundSummaryResponse> toOutboundSummaryResponse(List<Outbound> outbound);


    @Mapping(source = "shippingMethod", target = "shippingMethod", qualifiedByName = "mapShippingMethod")
    void updateOutboundFromRequest(UpdateOutboundRequest request, @MappingTarget Outbound outbound);
    @Named("isEditable")
    default boolean isEditable(LocalDate shippingDate) {
        return shippingDate == null || LocalDate.now().isBefore(shippingDate);
    }

    @Named("mapShippingMethod")
    default ShippingMethod mapShippingMethod(String shippingMethod) {
        if (shippingMethod == null) {
            return null;
        }
        return ShippingMethod.fromCode(shippingMethod);
    }

}
