package intern.rikkei.warehousesystem.service;

import intern.rikkei.warehousesystem.dto.auth.request.RegisterRequest;
import intern.rikkei.warehousesystem.dto.auth.request.UpdateProfileRequest;
import intern.rikkei.warehousesystem.dto.auth.response.UserResponse;

public interface UserService {
    UserResponse register(RegisterRequest registerRequest);
    UserResponse updateProfile(String userName, UpdateProfileRequest request);
    UserResponse getCurrentUser(String username);

}
