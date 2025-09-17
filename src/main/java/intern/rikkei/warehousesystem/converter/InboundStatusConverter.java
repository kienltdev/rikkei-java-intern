package intern.rikkei.warehousesystem.converter;

import intern.rikkei.warehousesystem.enums.InboundStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class InboundStatusConverter implements AttributeConverter<InboundStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(InboundStatus inboundStatus) {
        if (inboundStatus == null) {
            return null;
        }
        return inboundStatus.getCode();
    }

    @Override
    public InboundStatus convertToEntityAttribute(Integer inboundStatus) {
        if (inboundStatus == null) {
            return null;
        }
        return InboundStatus.fromCode(inboundStatus);
    }

}
