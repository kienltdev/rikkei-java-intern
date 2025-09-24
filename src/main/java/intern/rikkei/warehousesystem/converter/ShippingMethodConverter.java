package intern.rikkei.warehousesystem.converter;

import intern.rikkei.warehousesystem.enums.ShippingMethod;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ShippingMethodConverter implements AttributeConverter<ShippingMethod, String> {

    @Override
    public String convertToDatabaseColumn(ShippingMethod shippingMethod) {
        if(shippingMethod == null){
            return null;
        }
        return shippingMethod.getCode();
    }

    @Override
    public ShippingMethod convertToEntityAttribute(String code) {
        if(code == null){
            return null;
        }
        return ShippingMethod.fromCode(code);
    }

}
