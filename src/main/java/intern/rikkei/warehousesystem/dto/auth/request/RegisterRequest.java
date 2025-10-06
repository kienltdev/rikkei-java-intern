package intern.rikkei.warehousesystem.dto.auth.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "{validation.username.required}")
        @Size(min = 3, max = 50, message = "{validation.username.length}")
        @Pattern(regexp = "^[a-zA-Z0-9_.]+$", message = "{validation.username.format}")
        String username,

        @NotBlank(message = "{validation.password.required}")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "{validation.password.complexity}"
        )
        String password,

        @Size(max = 100, message = "{validation.fullName.length}")
        String fullName,

        @NotBlank(message = "{validation.email.required}")
        @Size(max = 254, message = "{validation.email.length}")
        @Email(message = "{validation.email.format}")
        String email
) {
}

