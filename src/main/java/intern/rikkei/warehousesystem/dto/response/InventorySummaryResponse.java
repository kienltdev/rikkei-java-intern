package intern.rikkei.warehousesystem.dto.response;

public record InventorySummaryResponse(
        Long totalQuantityInbound,
        Long totalQuantityAvailable
) {
}
