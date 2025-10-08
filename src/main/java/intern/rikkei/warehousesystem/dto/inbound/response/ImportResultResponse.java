package intern.rikkei.warehousesystem.dto.inbound.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(description = "Result of a bulk import operation from a file")
public record ImportResultResponse(
        @Schema(description = "Total number of rows processed from the file", example = "100")
        long totalRows,
        @Schema(description = "Number of rows that were successfully imported", example = "98")
        long successCount,
        @Schema(description = "Number of rows that failed to import", example = "2")
        long failureCount,
        @Schema(description = "List of details for each row that failed")
        List<ImportErrorDetail> errorDetails
) {
}