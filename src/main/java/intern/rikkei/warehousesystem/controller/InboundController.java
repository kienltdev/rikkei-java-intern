package intern.rikkei.warehousesystem.controller;

import intern.rikkei.warehousesystem.dto.common.PaginatedResponse;
import intern.rikkei.warehousesystem.dto.inbound.request.InboundRequest;
import intern.rikkei.warehousesystem.dto.inbound.request.InboundSearchRequest;
import intern.rikkei.warehousesystem.dto.inbound.request.InboundStatisticsRequest;
import intern.rikkei.warehousesystem.dto.inbound.request.UpdateInboundRequest;
import intern.rikkei.warehousesystem.dto.inbound.response.ImportResultResponse;
import intern.rikkei.warehousesystem.dto.inbound.response.InboundDetailResponse;
import intern.rikkei.warehousesystem.dto.inbound.response.InboundResponse;
import intern.rikkei.warehousesystem.dto.inbound.response.PaginatedInboundStatisticsResponse;
import intern.rikkei.warehousesystem.exception.ApiErrorResponse;
import intern.rikkei.warehousesystem.exception.InvalidOperationException;
import intern.rikkei.warehousesystem.service.InboundService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/warehouse/inbounds")
@RequiredArgsConstructor
@Validated
@Tag(name = "Inbound Management", description = "APIs for managing inbound shipments")
public class InboundController {

    private final InboundService inboundService;
    private final MessageSource messageSource;


    @Operation(summary = "Create a new inbound record", description = "Adds a single new inbound shipment to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Inbound record created successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = InboundResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PostMapping
    public ResponseEntity<InboundResponse> createInbound(@Valid @RequestBody InboundRequest request) {
        InboundResponse response = inboundService.createInbound(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @Operation(summary = "Search and list inbound records", description = "Retrieves a paginated list of inbound records, with optional filtering by product type and supplier code.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PaginatedResponse.class))), // Note: We should refine this schema later if needed
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @GetMapping
    public ResponseEntity<PaginatedResponse<InboundResponse>> getInbounds(
            @Valid InboundSearchRequest request, Pageable pageable) {

        Page<InboundResponse> inboundPage = inboundService.findAll(request, pageable);

        PaginatedResponse<InboundResponse> response = new PaginatedResponse<>(
                inboundPage.getContent(),
                inboundPage.getNumber() + 1,
                inboundPage.getSize(),
                inboundPage.getTotalPages(),
                inboundPage.getTotalElements()
        );

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update an inbound record", description = "Updates an existing inbound record. This is only allowed if the inbound has not been associated with any outbound shipment yet (status is NOT_OUTBOUND).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inbound record updated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = InboundResponse.class))),
            @ApiResponse(responseCode = "400", description = "Update not allowed due to business rule (e.g., already shipped) or invalid data",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Inbound record not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PutMapping("/{id}")
    public ResponseEntity<InboundResponse> updateInbound(@PathVariable Long id,
                                                         @Valid @RequestBody UpdateInboundRequest request) {
        InboundResponse response = inboundService.updateInbound(id, request);
        return ResponseEntity.ok(response);

    }

    @Operation(summary = "Import inbound records from a file", description = "Bulk imports inbound records from a CSV or Excel file. The file should contain specific columns: 'Supplier Country', 'Invoice', 'Product type', 'Quantity', 'Receive date'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "File processed successfully. See response body for details on success/failure counts.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ImportResultResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid file (e.g., empty, unsupported type, bad format)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PostMapping("/import")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ImportResultResponse> importInbounds(
            @Parameter(
                    description = "The CSV or Excel file to upload.",
                    required = true,
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            )
            @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            String message = messageSource.getMessage("error.file.empty", null, LocaleContextHolder.getLocale());
            throw new InvalidOperationException("EMPTY_FILE_UPLOADED", message);
        }

        ImportResultResponse result = inboundService.importFromFile(file);
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "Delete an inbound record", description = "Deletes an inbound record. This is only allowed if the record has not been associated with any outbound shipment yet (status is NOT_OUTBOUND).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Inbound record deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Deletion not allowed due to business rule (e.g., already shipped)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Inbound record not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInbound(
            @Parameter(description = "ID of the inbound record to delete", required = true, example = "1")
            @PathVariable Long id) {
        inboundService.deleteInbound(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get inbound statistics", description = "Retrieves paginated statistics of inbound records, grouped by product type and supplier code. It also provides a grand total of quantities for the current filter.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved statistics",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PaginatedInboundStatisticsResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @GetMapping("/statistics")
    public ResponseEntity<PaginatedInboundStatisticsResponse> getStatistics(
            @Valid InboundStatisticsRequest request, Pageable pageable) {

        PaginatedInboundStatisticsResponse response = inboundService.getInboundStatistics(request, pageable);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get a single inbound record by ID", description = "Retrieves detailed information for a specific inbound shipment, including its associated outbounds.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inbound record found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = InboundDetailResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Inbound record not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<InboundDetailResponse>  getInboundDetail(
            @Parameter(description = "ID of the inbound record to retrieve", required = true, example = "1")
            @PathVariable Long id) {
        InboundDetailResponse response = inboundService.findInboundDetailById(id);
        return ResponseEntity.ok(response);
    }

}
