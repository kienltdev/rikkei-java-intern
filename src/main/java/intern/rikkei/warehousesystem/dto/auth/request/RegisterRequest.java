package intern.rikkei.warehousesystem.dto.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Request body for user registration")
public record RegisterRequest(
        @Schema(
                description = "Unique username (3–50 chars). Letters, digits, underscores and dots only.",
                example = "kien.le"
        )
        @NotBlank(message = "{validation.username.required}")
        @Size(min = 3, max = 50, message = "{validation.username.length}")
        @Pattern(regexp = "^[a-zA-Z0-9_.]+$", message = "{validation.username.format}")
        String username,

        @Schema(
                description = "Password (min 8 chars) with at least 1 uppercase, 1 lowercase, 1 digit, and 1 special symbol.",
                example = "Password@123"
        )
        @NotBlank(message = "{validation.password.required}")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "{validation.password.complexity}"
        )
        String password,

        @Schema(
                description = "Full name (max 100 chars). Optional field.",
                example = "Le Trung Kien"
        )
        @Size(max = 100, message = "{validation.fullName.length}")
        String fullName,

        @Schema(
                description = "Unique email address (max 254 chars), must follow standard email format.",
                example = "kien.le@example.com"
        )
        @NotBlank(message = "{validation.email.required}")
        @Size(max = 254, message = "{validation.email.length}")
        @Email(message = "{validation.email.format}")
        String email
) {
}

