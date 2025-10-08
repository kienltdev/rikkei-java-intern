package intern.rikkei.warehousesystem.dto.inventory.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "A summary of inventory quantities based on the provided filters.")
public record InventorySummaryResponse(
        @Schema(description = "The total quantity of all products ever received that match the filters.", example = "10000")
        Long totalQuantityInbound,

        @Schema(description = "The current available quantity in stock that matches the filters (Total Inbound - Total Outbound).", example = "4500")
        Long totalQuantityAvailable
) {
}
