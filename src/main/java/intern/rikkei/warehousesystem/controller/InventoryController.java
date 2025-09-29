package intern.rikkei.warehousesystem.controller;

import intern.rikkei.warehousesystem.dto.request.InventorySearchRequest;
import intern.rikkei.warehousesystem.dto.response.InventorySummaryResponse;
import intern.rikkei.warehousesystem.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
}
