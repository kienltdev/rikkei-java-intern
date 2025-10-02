package intern.rikkei.warehousesystem.controller;

import intern.rikkei.warehousesystem.dto.request.OutboundRequest;
import intern.rikkei.warehousesystem.dto.request.OutboundSearchRequest;
import intern.rikkei.warehousesystem.dto.request.UpdateOutboundRequest;
import intern.rikkei.warehousesystem.dto.response.OutboundDetailResponse;
import intern.rikkei.warehousesystem.dto.response.OutboundResponse;
import intern.rikkei.warehousesystem.dto.response.PaginatedResponse;
import intern.rikkei.warehousesystem.service.OutboundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouse/outbounds")
@RequiredArgsConstructor
@Validated
public class OutboundController {
    private final OutboundService outboundService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<PaginatedResponse<OutboundResponse>> getOutbounds(
            @Valid OutboundSearchRequest request,
            Pageable pageable
    ){
        Page<OutboundResponse> outboundResponses = outboundService.findAll(request, pageable);
        PaginatedResponse<OutboundResponse> response = new PaginatedResponse<>(
                outboundResponses.getContent(),
                outboundResponses.getNumber() + 1,
                outboundResponses.getSize(),
                outboundResponses.getTotalPages(),
                outboundResponses.getTotalElements()
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<OutboundResponse> createOutbound(@Valid @RequestBody OutboundRequest request) {
        OutboundResponse response = outboundService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<OutboundDetailResponse>  getOutboundDetail(@PathVariable Long id) {
        OutboundDetailResponse response = outboundService.findById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<OutboundResponse> updateOutbound(@PathVariable Long id, @Valid @RequestBody UpdateOutboundRequest request) {
        OutboundResponse response = outboundService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<Void> deleteOutbound(@PathVariable Long id) {
        outboundService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
