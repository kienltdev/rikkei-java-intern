package intern.rikkei.warehousesystem.service;

import intern.rikkei.warehousesystem.dto.request.InventoryListRequest;
import intern.rikkei.warehousesystem.dto.request.InventorySearchRequest;
import intern.rikkei.warehousesystem.dto.response.InventoryDetailResponse;
import intern.rikkei.warehousesystem.dto.response.InventorySummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InventoryService {
    InventorySummaryResponse getInventorySummary(InventorySearchRequest request);
    Page<InventoryDetailResponse> getInventoryDetails(InventoryListRequest request, Pageable pageable);
}
