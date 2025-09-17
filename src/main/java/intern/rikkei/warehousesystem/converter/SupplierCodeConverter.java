package intern.rikkei.warehousesystem.converter;

import intern.rikkei.warehousesystem.enums.SupplierCode;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SupplierCodeConverter implements AttributeConverter<SupplierCode, String> {

    @Override
    public String convertToDatabaseColumn(SupplierCode supplierCode) {
        if (supplierCode == null) {
            return null;
        }
        return supplierCode.getCode();
    }

    @Override
    public SupplierCode convertToEntityAttribute(String supplierCode) {
        if (supplierCode == null) {
            return null;
        }
        return SupplierCode.fromCode(supplierCode);
    }
}
