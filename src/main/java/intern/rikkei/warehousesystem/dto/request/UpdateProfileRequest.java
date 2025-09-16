package intern.rikkei.warehousesystem.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(
        @Size(min = 1, max = 100, message = "{validation.fullName.length}")
        String fullName,

        @Size(max = 254, message = "{validation.email.length}")
        @Email(message = "{validation.email.format}")
        String email) {
}
