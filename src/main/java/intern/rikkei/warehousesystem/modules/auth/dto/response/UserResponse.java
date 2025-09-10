package intern.rikkei.warehousesystem.modules.auth.dto.response;
import intern.rikkei.warehousesystem.common.enums.Role;
public record UserResponse(Long id, String userName, String fullName, String email, Role role){ }
