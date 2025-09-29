package intern.rikkei.warehousesystem.service;

import intern.rikkei.warehousesystem.dto.request.InventorySearchRequest;
import intern.rikkei.warehousesystem.dto.response.InventorySummaryResponse;

public interface InventoryService {
    InventorySummaryResponse getInventorySummary(InventorySearchRequest request);
}
