package intern.rikkei.warehousesystem.controller;

import intern.rikkei.warehousesystem.dto.request.InboundRequest;
import intern.rikkei.warehousesystem.dto.request.InboundSearchRequest;
import intern.rikkei.warehousesystem.dto.request.UpdateInboundRequest;
import intern.rikkei.warehousesystem.dto.response.InboundResponse;
import intern.rikkei.warehousesystem.dto.response.PaginatedResponse;
import intern.rikkei.warehousesystem.service.InboundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouse/inbounds")
@RequiredArgsConstructor
@Validated
public class InboundController {

    private final InboundService inboundService;

    @PostMapping
    public ResponseEntity<InboundResponse> createInbound(@Valid @RequestBody InboundRequest request) {
        InboundResponse response = inboundService.createInbound(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<InboundResponse>> getInbounds(
            @Valid InboundSearchRequest request) {

        Page<InboundResponse> inboundPage = inboundService.findAll(request);

        PaginatedResponse<InboundResponse> response = new PaginatedResponse<>(
                inboundPage.getContent(),
                inboundPage.getNumber(),
                inboundPage.getSize(),
                inboundPage.getTotalPages(),
                inboundPage.getTotalElements()
        );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InboundResponse> updateInbound(@PathVariable Long id,
                                                         @Valid @RequestBody UpdateInboundRequest request) {
        InboundResponse response = inboundService.updateInbound(id, request);
        return ResponseEntity.ok(response);

    }
}
