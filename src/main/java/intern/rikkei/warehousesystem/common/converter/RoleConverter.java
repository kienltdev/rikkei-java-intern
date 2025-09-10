package intern.rikkei.warehousesystem.common.converter;

import intern.rikkei.warehousesystem.common.enums.Role;
import jakarta.persistence.AttributeConverter;

public class RoleConverter implements AttributeConverter<Role, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Role role){
        if(role == null){
            return null;
        }
        return role.getValue();
    }

    @Override
    public Role convertToEntityAttribute(Integer dbData){
        if(dbData == null){
            return null;
        }
        return Role.fromValue(dbData);
    }
}
