package intern.rikkei.warehousesystem.service.impl;

import intern.rikkei.warehousesystem.dto.request.InventoryListRequest;
import intern.rikkei.warehousesystem.dto.request.InventorySearchRequest;
import intern.rikkei.warehousesystem.dto.response.InventoryDetailResponse;
import intern.rikkei.warehousesystem.dto.response.InventorySummaryResponse;
import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCd;
import intern.rikkei.warehousesystem.repository.InboundRepository;
import intern.rikkei.warehousesystem.repository.OutboundRepository;
import intern.rikkei.warehousesystem.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InboundRepository inboundRepository;
    private final OutboundRepository outboundRepository;

    @Transactional(readOnly = true)
    @Override
    public InventorySummaryResponse getInventorySummary(InventorySearchRequest request) {
        ProductType productType = StringUtils.hasText(request.productType()) ? ProductType.valueOf(request.productType().toUpperCase()) : null;
        SupplierCd supplierCd = StringUtils.hasText(request.supplierCd()) ? SupplierCd.valueOf(request.supplierCd().toUpperCase()) : null;
        String invoice = request.invoice();

        Long totalQuantityInbound = inboundRepository.sumQuantityByFilters(productType, supplierCd, invoice);
        Long totalQuantityOutbound = outboundRepository.sumQuantityByInboundFilter(productType, supplierCd, invoice);
        Long totalQuantityAvailable = totalQuantityInbound - totalQuantityOutbound;

        return new InventorySummaryResponse(
                totalQuantityInbound,
                totalQuantityAvailable
        );
    }


    @Override
    @Transactional(readOnly = true)
    public Page<InventoryDetailResponse> getInventoryDetails(InventoryListRequest request, Pageable pageable) {
        ProductType productType = StringUtils.hasText(request.productType()) ? ProductType.valueOf(request.productType()) : null;
        SupplierCd supplierCd = StringUtils.hasText(request.supplierCd()) ? SupplierCd.fromCode(request.supplierCd()) : null;


        return inboundRepository.findInventoryDetails(
                request.inbId(),
                request.invoice(),
                productType,
                supplierCd,
                pageable
        );
    }

}
