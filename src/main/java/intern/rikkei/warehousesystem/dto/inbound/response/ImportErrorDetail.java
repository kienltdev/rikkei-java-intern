package intern.rikkei.warehousesystem.dto.inbound.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Details of an error that occurred during file import for a specific row")
public record ImportErrorDetail(
        @Schema(description = "The row number in the original file where the error occurred (2-based index)", example = "5")
        int rowNumber,
        @Schema(description = "A message describing the validation error", example = "Invalid date format. Please use dd/MM/yyyy")
        String message
) {
}