package intern.rikkei.warehousesystem.dto.inbound.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ImportResultResponse(
        long totalRows,
        long successCount,
        long failureCount,
        List<ImportErrorDetail> errorDetails
) {
}