package intern.rikkei.warehousesystem.converter;

import intern.rikkei.warehousesystem.enums.SupplierCd;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class SupplierCodeConverter implements AttributeConverter<SupplierCd, String> {

    @Override
    public String convertToDatabaseColumn(SupplierCd supplierCd) {
        if (supplierCd == null) {
            return null;
        }
        return supplierCd.getCode();
    }

    @Override
    public SupplierCd convertToEntityAttribute(String supplierCode) {
        if (supplierCode == null) {
            return null;
        }
        return intern.rikkei.warehousesystem.enums.SupplierCd.fromCode(supplierCode);
    }
}
