package intern.rikkei.warehousesystem.dto.inventory.response;

public record InventorySummaryResponse(
        Long totalQuantityInbound,
        Long totalQuantityAvailable
) {
}
