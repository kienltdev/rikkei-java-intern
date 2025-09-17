package intern.rikkei.warehousesystem.controller;

import intern.rikkei.warehousesystem.constant.ApiConstants;
import intern.rikkei.warehousesystem.dto.request.InboundRequest;
import intern.rikkei.warehousesystem.dto.response.InboundResponse;
import intern.rikkei.warehousesystem.dto.response.PaginatedResponse;
import intern.rikkei.warehousesystem.service.InboundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/warehouse/inbounds")
@RequiredArgsConstructor
public class InboundController {

    private final InboundService inboundService;

    @PostMapping
    public ResponseEntity<InboundResponse> createInbound(@Valid @RequestBody InboundRequest request) {
        InboundResponse response = inboundService.createInbound(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<InboundResponse>> getInbounds(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = ApiConstants.DEFAULT_PAGE_SIZE + "") int size,
            @RequestParam(required = false) String productType,
            @RequestParam(required = false) String supplierCd) {

        Page<InboundResponse> inboundPage = inboundService.findAll(page, size, productType, supplierCd);

        PaginatedResponse<InboundResponse> response = new PaginatedResponse<>(
                inboundPage.getContent(),
                inboundPage.getNumber(),
                inboundPage.getSize(),
                inboundPage.getTotalPages(),
                inboundPage.getTotalElements()
        );

        return ResponseEntity.ok(response);
    }
}
