package intern.rikkei.warehousesystem.mapper;

import intern.rikkei.warehousesystem.dto.response.OutboundResponse;
import intern.rikkei.warehousesystem.entity.Outbound;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;

@Mapper(componentModel = "spring", imports = {LocalDate.class})
public interface OutboundMapper {
    @Mapping(source = "inbound.id", target = "inbId")
    @Mapping(source = "shippingDate", target = "editable", qualifiedByName = "isEditable")
    OutboundResponse toOutboundResponse(Outbound outbound);

    @Named("isEditable")
    default boolean isEditable(LocalDate shippingDate) {
        return shippingDate == null || shippingDate.isBefore(LocalDate.now());
    }

}
