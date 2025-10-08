package intern.rikkei.warehousesystem.dto.common;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Generic response for paginated data. The 'content' will contain a list of specific DTOs.")
public record PaginatedResponse<T>(
        @Schema(description = "The list of items for the current page.")
        List<T> content,
        @Schema(description = "The current page number (one-based index).", example = "1")
        int currentPage,
        @Schema(description = "The number of items requested per page.", example = "20")
        int pageSize,
        @Schema(description = "The total number of pages available.", example = "10")
        int totalPages,
        @Schema(description = "The total number of items across all pages.", example = "198")
        long totalItems
){}