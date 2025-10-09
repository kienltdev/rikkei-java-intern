package intern.rikkei.warehousesystem.dto.auth.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateProfileRequest(

        @Schema(
                description = "Full name (max 100 chars). Optional field.",
                example = "Le Trung Kien"
        )
        @Size(max = 100, message = "{validation.fullName.length}")
        String fullName,

        @Schema(
                description = "Unique email address (max 254 chars), must follow standard email format. Optional field.",
                example = "kien.le@example.com"
        )
        @Size(max = 254, message = "{validation.email.length}")
        @Email(message = "{validation.email.format}")
        String email) {
}
