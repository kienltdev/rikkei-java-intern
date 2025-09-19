package intern.rikkei.warehousesystem.controller;

import intern.rikkei.warehousesystem.dto.request.InboundRequest;
import intern.rikkei.warehousesystem.dto.request.InboundSearchRequest;
import intern.rikkei.warehousesystem.dto.request.UpdateInboundRequest;
import intern.rikkei.warehousesystem.dto.response.ImportResultResponse;
import intern.rikkei.warehousesystem.dto.response.InboundResponse;
import intern.rikkei.warehousesystem.dto.response.PaginatedResponse;
import intern.rikkei.warehousesystem.service.InboundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
            // Đây là một validation cơ bản, có thể trả về lỗi BAD_REQUEST
            // Tạm thời để đơn giản, ta sẽ để Service xử lý
        }

        ImportResultResponse result = inboundService.importFromExcel(file);
        return ResponseEntity.ok(result);
    }
}
