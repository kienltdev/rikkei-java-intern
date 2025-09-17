package intern.rikkei.warehousesystem.service;

import intern.rikkei.warehousesystem.dto.request.InboundRequest;
import intern.rikkei.warehousesystem.dto.response.InboundResponse;
import org.springframework.data.domain.Page;

public interface InboundService {
    InboundResponse createInbound(InboundRequest request);
    Page<InboundResponse> findAll(int page, int size, String productType, String supplierCd);
}
