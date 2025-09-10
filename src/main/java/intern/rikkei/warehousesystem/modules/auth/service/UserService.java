package intern.rikkei.warehousesystem.modules.auth.service;

import intern.rikkei.warehousesystem.modules.auth.dto.request.RegisterRequest;
import intern.rikkei.warehousesystem.modules.auth.dto.request.UpdateProfileRequest;
import intern.rikkei.warehousesystem.modules.auth.dto.response.UserResponse;

public interface UserService {
    UserResponse register(RegisterRequest registerRequest);
    UserResponse updateProfile(String userName, UpdateProfileRequest request);
    UserResponse getCurrentUser(String username);

}
