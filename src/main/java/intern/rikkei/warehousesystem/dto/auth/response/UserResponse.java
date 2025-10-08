package intern.rikkei.warehousesystem.dto.auth.response;

import intern.rikkei.warehousesystem.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response body containing user details")
public record UserResponse(
        @Schema(description = "User's unique identifier", example = "1")
        Long id,
        @Schema(description = "Username", example = "kien.le")
        String username,
        @Schema(description = "User's full name", example = "Le Trung Kien")
        String fullName,
        @Schema(description = "User's email address", example = "kien.le@example.com")
        String email,
        @Schema(description = "User's role", example = "2")
        Role role){ }
