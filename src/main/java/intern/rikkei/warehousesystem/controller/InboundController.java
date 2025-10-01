package intern.rikkei.warehousesystem.controller;

import intern.rikkei.warehousesystem.dto.request.InboundRequest;
import intern.rikkei.warehousesystem.dto.request.InboundSearchRequest;
import intern.rikkei.warehousesystem.dto.request.InboundStatisticsRequest;
import intern.rikkei.warehousesystem.dto.request.UpdateInboundRequest;
import intern.rikkei.warehousesystem.dto.response.*;
import intern.rikkei.warehousesystem.service.InboundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/warehouse/inbounds")
@RequiredArgsConstructor
@Validated
public class InboundController {

    private final InboundService inboundService;

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PostMapping
    public ResponseEntity<InboundResponse> createInbound(@Valid @RequestBody InboundRequest request) {
        InboundResponse response = inboundService.createInbound(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

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

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @PutMapping("/{id}")
    public ResponseEntity<InboundResponse> updateInbound(@PathVariable Long id,
                                                         @Valid @RequestBody UpdateInboundRequest request) {
        InboundResponse response = inboundService.updateInbound(id, request);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/import")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<ImportResultResponse> importInbounds(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {

        }

        ImportResultResponse result = inboundService.importFromExcel(file);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInbound(@PathVariable Long id) {
        inboundService.deleteInbound(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @GetMapping("/statistics")
    public ResponseEntity<PaginatedInboundStatisticsResponse> getStatistics(
            @Valid InboundStatisticsRequest request, Pageable pageable) {

        PaginatedInboundStatisticsResponse response = inboundService.getInboundStatistics(request, pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<InboundDetailResponse>  getInboundDetail(@PathVariable Long id) {
        InboundDetailResponse response = inboundService.findInboundDetailById(id);
        return ResponseEntity.ok(response);
    }

}
