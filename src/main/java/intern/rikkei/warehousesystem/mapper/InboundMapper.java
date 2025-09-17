package intern.rikkei.warehousesystem.mapper;

import intern.rikkei.warehousesystem.dto.request.InboundRequest;
import intern.rikkei.warehousesystem.dto.response.InboundResponse;
import intern.rikkei.warehousesystem.entity.Inbound;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InboundMapper {
    Inbound toInbound(InboundRequest request);

    @Mapping(target = "editable", expression = "java(inbound.getStatus() == intern.rikkei.warehousesystem.enums.InboundStatus.NOT_OUTBOUND)")
    InboundResponse toInboundResponse(Inbound inbound);
}