package intern.rikkei.warehousesystem.modules.auth.controller;

import intern.rikkei.warehousesystem.modules.auth.dto.request.RegisterRequest;
import intern.rikkei.warehousesystem.modules.auth.dto.request.UpdateProfileRequest;
import intern.rikkei.warehousesystem.modules.auth.dto.response.UserResponse;
import intern.rikkei.warehousesystem.modules.auth.entity.User;
import intern.rikkei.warehousesystem.modules.auth.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/warehouse/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        UserResponse registerUser = userService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(registerUser);
    }

    @GetMapping("/login")
    public ResponseEntity<UserResponse> login(Principal principal) {
        UserResponse currentUser = userService.getCurrentUser(principal.getName());
        return ResponseEntity.ok(currentUser);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserResponse> updateProfile(Principal principal, @RequestBody UpdateProfileRequest request) {
        UserResponse updatedUser = userService.updateProfile(principal.getName(), request);
        return ResponseEntity.ok(updatedUser);
    }
}
