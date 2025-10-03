package intern.rikkei.warehousesystem.controller;

import intern.rikkei.warehousesystem.dto.response.DictionaryResponseDTO;
import intern.rikkei.warehousesystem.service.DictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/warehouse")
@RequiredArgsConstructor
public class DictionaryController {

    private final DictionaryService dictionaryService;

    @GetMapping("/product-types")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<DictionaryResponseDTO> getProductTypes() {
        return ResponseEntity.ok(dictionaryService.getProductTypes());
    }

    @GetMapping("/supplier-codes")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<DictionaryResponseDTO> getSupplierCodes() {
        return ResponseEntity.ok(dictionaryService.getSupplierCodes());
    }

    @GetMapping("/shipping-methods")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<DictionaryResponseDTO> getShippingMethods() {
        return ResponseEntity.ok(dictionaryService.getShippingMethods());
    }

    @GetMapping("/inbound/statuses")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    public ResponseEntity<DictionaryResponseDTO> getInboundStatuses() {
        return ResponseEntity.ok(dictionaryService.getInboundStatuses());
    }
}
