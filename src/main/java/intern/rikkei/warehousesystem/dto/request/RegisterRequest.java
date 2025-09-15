package intern.rikkei.warehousesystem.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "{NotBlank.registerRequest.email}")
        @Size(min = 3, max = 50, message = "{Size.registerRequest.username}")
        String username,

        @NotBlank(message = "{NotBlank.registerRequest.password}")
        @Size(min = 6, message = "{Size.registerRequest.password}")
        String password,

        String fullName,

        @NotBlank(message = "{NotBlank.registerRequest.email}")
        @Email(message = "{Email.registerRequest.email}")
        String email
) {
}

