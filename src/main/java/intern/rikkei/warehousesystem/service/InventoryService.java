package intern.rikkei.warehousesystem.service;

import intern.rikkei.warehousesystem.dto.inventory.request.InventoryListRequest;
import intern.rikkei.warehousesystem.dto.inventory.request.InventorySearchRequest;
import intern.rikkei.warehousesystem.dto.inventory.response.InventoryDetailResponse;
import intern.rikkei.warehousesystem.dto.inventory.response.InventorySummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryService {
    InventorySummaryResponse getInventorySummary(InventorySearchRequest request);
    Page<InventoryDetailResponse> getInventoryDetails(InventoryListRequest request, Pageable pageable);
}
