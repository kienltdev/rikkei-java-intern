package intern.rikkei.warehousesystem.modules.user.dto.service;

import intern.rikkei.warehousesystem.modules.user.dto.request.RegisterRequest;
import intern.rikkei.warehousesystem.modules.user.dto.request.UpdateProfileRequest;
import intern.rikkei.warehousesystem.modules.user.dto.response.UserResponse;

public interface UserService {
    UserResponse register(RegisterRequest registerRequest);
    UserResponse updateProfile(String userName, UpdateProfileRequest request);
    UserResponse getCurrentUser(String username);

}
