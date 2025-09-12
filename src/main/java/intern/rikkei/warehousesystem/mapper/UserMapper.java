package intern.rikkei.warehousesystem.mapper;

import intern.rikkei.warehousesystem.dto.response.UserResponse;
import intern.rikkei.warehousesystem.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
}
