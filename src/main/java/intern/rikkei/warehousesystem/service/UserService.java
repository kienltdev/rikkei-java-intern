package intern.rikkei.warehousesystem.service;

import intern.rikkei.warehousesystem.dto.request.RegisterRequest;
import intern.rikkei.warehousesystem.dto.request.UpdateProfileRequest;
import intern.rikkei.warehousesystem.dto.response.UserResponse;

public interface UserService {
    UserResponse register(RegisterRequest registerRequest);
    UserResponse updateProfile(String userName, UpdateProfileRequest request);
    UserResponse getCurrentUser(String username);

}
