package intern.rikkei.warehousesystem.modules.auth.mapper;

import intern.rikkei.warehousesystem.modules.auth.dto.response.UserResponse;
import intern.rikkei.warehousesystem.modules.auth.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
}
