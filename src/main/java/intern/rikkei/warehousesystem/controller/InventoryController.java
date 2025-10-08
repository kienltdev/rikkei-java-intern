package intern.rikkei.warehousesystem.controller;

import intern.rikkei.warehousesystem.dto.common.PaginatedResponse;
import intern.rikkei.warehousesystem.dto.inventory.request.InventoryListRequest;
import intern.rikkei.warehousesystem.dto.inventory.request.InventorySearchRequest;
import intern.rikkei.warehousesystem.dto.inventory.response.InventoryDetailResponse;
import intern.rikkei.warehousesystem.dto.inventory.response.InventorySummaryResponse;
import intern.rikkei.warehousesystem.exception.ApiErrorResponse;
import intern.rikkei.warehousesystem.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/warehouse/inventory")
@RequiredArgsConstructor
@Tag(name = "Inventory Management", description = "APIs for querying inventory status")
public class InventoryController {
    private final InventoryService inventoryService;

    @Operation(summary = "Get inventory summary", description = "Calculates and returns the total quantity inbound and total quantity available based on optional filters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved summary",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = InventorySummaryResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/summary")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<InventorySummaryResponse> getInventorySummary(@Valid InventorySearchRequest request){
        InventorySummaryResponse response = inventoryService.getInventorySummary(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get detailed inventory list", description = "Retrieves a paginated list of inventory details for each inbound record, including total and available quantities, based on filters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PaginatedResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class)))
    })
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
