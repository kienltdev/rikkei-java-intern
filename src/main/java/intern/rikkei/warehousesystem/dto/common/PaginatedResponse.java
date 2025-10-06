package intern.rikkei.warehousesystem.dto.common;

import java.util.List;

public record PaginatedResponse<T>(
        List<T> content,
        int currentPage,
        int pageSize,
        int totalPages,
        long totalItems
){}