package intern.rikkei.warehousesystem.controller;

import intern.rikkei.warehousesystem.dto.common.PaginatedResponse;
import intern.rikkei.warehousesystem.dto.outbound.request.OutboundRequest;
import intern.rikkei.warehousesystem.dto.outbound.request.OutboundSearchRequest;
import intern.rikkei.warehousesystem.dto.outbound.request.UpdateOutboundRequest;
import intern.rikkei.warehousesystem.dto.outbound.response.OutboundDetailResponse;
import intern.rikkei.warehousesystem.dto.outbound.response.OutboundListResponse;
import intern.rikkei.warehousesystem.dto.outbound.response.OutboundResponse;
import intern.rikkei.warehousesystem.exception.ApiErrorResponse;
import intern.rikkei.warehousesystem.service.OutboundService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouse/outbounds")
@RequiredArgsConstructor
@Validated
@Tag(name = "Outbound Management", description = "APIs for managing outbound shipments")
public class OutboundController {
    private final OutboundService outboundService;

    @Operation(summary = "Search and list outbound records", description = "Retrieves a paginated list of all outbound records.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PaginatedResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<PaginatedResponse<OutboundListResponse>> getOutbounds(
            @Valid OutboundSearchRequest request,
            Pageable pageable
    ){
        Page<OutboundListResponse> outboundResponses = outboundService.findAll(request, pageable);
        PaginatedResponse<OutboundListResponse> response = new PaginatedResponse<>(
                outboundResponses.getContent(),
                outboundResponses.getNumber() + 1,
                outboundResponses.getSize(),
                outboundResponses.getTotalPages(),
                outboundResponses.getTotalElements()
        );

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create a new outbound record", description = "Creates a new outbound shipment linked to an existing inbound record.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Outbound record created successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = OutboundResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data or business rule violation (e.g., insufficient quantity)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Associated inbound record not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<OutboundResponse> createOutbound(@Valid @RequestBody OutboundRequest request) {
        OutboundResponse response = outboundService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get a single outbound record by ID", description = "Retrieves detailed information for a specific outbound shipment.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Outbound record found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = OutboundDetailResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Outbound record not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<OutboundDetailResponse>  getOutboundDetail(@PathVariable Long id) {
        OutboundDetailResponse response = outboundService.findById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update an outbound record", description = "Updates an existing outbound record. This is only allowed if the shipping date has not passed.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Outbound record updated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = OutboundResponse.class))),
            @ApiResponse(responseCode = "400", description = "Update not allowed or invalid data",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Outbound record not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<OutboundResponse> updateOutbound(@PathVariable Long id, @Valid @RequestBody UpdateOutboundRequest request) {
        OutboundResponse response = outboundService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete an outbound record", description = "Deletes an outbound record. This is only allowed if the shipping date has not passed.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Outbound record deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Deletion not allowed",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Outbound record not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<Void> deleteOutbound(@PathVariable Long id) {
        outboundService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
