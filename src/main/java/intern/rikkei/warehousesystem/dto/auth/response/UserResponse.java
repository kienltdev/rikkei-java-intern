package intern.rikkei.warehousesystem.dto.auth.response;
import intern.rikkei.warehousesystem.enums.Role;
public record UserResponse(Long id, String username, String fullName, String email, Role role){ }
