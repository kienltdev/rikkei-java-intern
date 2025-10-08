package intern.rikkei.warehousesystem.dto.inbound.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "A paginated response for inbound statistics, including a grand total for the current filter.")
public record PaginatedInboundStatisticsResponse(
        @Schema(description = "The list of statistical summaries for the current page.")
        List<InboundStatisticsResponse> content,
        @Schema(description = "The grand total quantity across all pages for the current filter.", example = "5000")
        Long grandTotalQuantity,
        @Schema(description = "The current page number (one-based index).", example = "1")
        int currentPage,
        @Schema(description = "The number of items requested per page.", example = "20")
        int pageSize,
        @Schema(description = "The total number of pages available.", example = "2")
        int totalPages,
        @Schema(description = "The total number of statistical groups across all pages.", example = "25")
        long totalItems
) {
}
