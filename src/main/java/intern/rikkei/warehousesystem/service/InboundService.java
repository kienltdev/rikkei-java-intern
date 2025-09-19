package intern.rikkei.warehousesystem.service;

import intern.rikkei.warehousesystem.dto.request.InboundRequest;
import intern.rikkei.warehousesystem.dto.request.InboundSearchRequest;
import intern.rikkei.warehousesystem.dto.request.UpdateInboundRequest;
import intern.rikkei.warehousesystem.dto.response.InboundResponse;
import org.springframework.data.domain.Page;

public interface InboundService {
    InboundResponse createInbound(InboundRequest request);
    Page<InboundResponse> findAll(InboundSearchRequest request);
    InboundResponse updateInbound(Long id, UpdateInboundRequest request);
}
