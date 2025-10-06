package intern.rikkei.warehousesystem.controller;

import intern.rikkei.warehousesystem.dto.inventory.request.InventoryListRequest;
import intern.rikkei.warehousesystem.dto.inventory.request.InventorySearchRequest;
import intern.rikkei.warehousesystem.dto.inventory.response.InventoryDetailResponse;
import intern.rikkei.warehousesystem.dto.inventory.response.InventorySummaryResponse;
import intern.rikkei.warehousesystem.dto.common.PaginatedResponse;
import intern.rikkei.warehousesystem.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/warehouse/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<InventorySummaryResponse> getInventorySummary(@Valid InventorySearchRequest request){
        InventorySummaryResponse response = inventoryService.getInventorySummary(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<PaginatedResponse<InventoryDetailResponse>> getInventoryDetails(
            @Valid InventoryListRequest request, Pageable pageable){
        Page<InventoryDetailResponse> inventoryPage  = inventoryService.getInventoryDetails(request, pageable);
        PaginatedResponse<InventoryDetailResponse> response = new PaginatedResponse<>(
                inventoryPage.getContent(),
                inventoryPage.getNumber() + 1,
                inventoryPage.getSize(),
                inventoryPage.getTotalPages(),
                inventoryPage.getTotalElements()

        );
        return ResponseEntity.ok(response);

    }
}
