package intern.rikkei.warehousesystem.dto.inbound.response;

import java.util.List;

public record PaginatedInboundStatisticsResponse(
        List<InboundStatisticsResponse> content,

        Long grandTotalQuantity,

        int currentPage,
        int pageSize,
        int totalPages,
        long totalItems
) {
}
