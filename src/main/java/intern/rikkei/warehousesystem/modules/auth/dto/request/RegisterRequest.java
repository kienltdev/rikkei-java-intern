package intern.rikkei.warehousesystem.modules.auth.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Username must not be empty")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        String userName,

        @NotBlank(message = "Password must not be empty")
        @Size(min = 6, message = "Password must have at least 6 characters")
        String passWord,

        String fullName,

        @NotBlank(message = "Email must not be empty")
        @Email(message = "Email is not valid")
        String email
) {
}

