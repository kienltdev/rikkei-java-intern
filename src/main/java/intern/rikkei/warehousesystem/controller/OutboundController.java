package intern.rikkei.warehousesystem.controller;

import intern.rikkei.warehousesystem.dto.request.OutboundSearchRequest;
import intern.rikkei.warehousesystem.dto.response.OutboundResponse;
import intern.rikkei.warehousesystem.dto.response.PaginatedResponse;
import intern.rikkei.warehousesystem.service.OutboundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
