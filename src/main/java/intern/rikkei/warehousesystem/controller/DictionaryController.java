package intern.rikkei.warehousesystem.controller;

import intern.rikkei.warehousesystem.dto.common.DictionaryResponseDTO;
import intern.rikkei.warehousesystem.exception.ApiErrorResponse;
import intern.rikkei.warehousesystem.service.DictionaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/warehouse")
@RequiredArgsConstructor
@Tag(name = "Dictionaries", description = "APIs to retrieve lists of predefined values (e.g., for dropdowns)")
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @Operation(summary = "Get all product types", description = "Returns a list of all available product types with their codes and display names.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved product types",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DictionaryResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/product-types")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<DictionaryResponseDTO> getProductTypes() {
        return ResponseEntity.ok(dictionaryService.getProductTypes());
    }

    @Operation(summary = "Get all supplier codes", description = "Returns a list of all available supplier country codes with their codes and display names.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved supplier codes")})
    @GetMapping("/supplier-codes")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<DictionaryResponseDTO> getSupplierCodes() {
        return ResponseEntity.ok(dictionaryService.getSupplierCodes());
    }

    @Operation(summary = "Get all shipping methods", description = "Returns a list of all available shipping methods with their codes and display names.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved shipping methods")})
    @GetMapping("/shipping-methods")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<DictionaryResponseDTO> getShippingMethods() {
        return ResponseEntity.ok(dictionaryService.getShippingMethods());
    }

    @Operation(summary = "Get all inbound statuses", description = "Returns a list of all possible inbound statuses with their codes and display names.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successfully retrieved inbound statuses")})
    @GetMapping("/inbound/statuses")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<DictionaryResponseDTO> getInboundStatuses() {
        return ResponseEntity.ok(dictionaryService.getInboundStatuses());
    }
}
