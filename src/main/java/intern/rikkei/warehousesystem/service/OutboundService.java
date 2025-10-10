package intern.rikkei.warehousesystem.service;

import intern.rikkei.warehousesystem.dto.outbound.request.OutboundRequest;
import intern.rikkei.warehousesystem.dto.outbound.request.OutboundSearchRequest;
import intern.rikkei.warehousesystem.dto.outbound.request.UpdateOutboundRequest;
import intern.rikkei.warehousesystem.dto.outbound.response.OutboundDetailResponse;
import intern.rikkei.warehousesystem.dto.outbound.response.OutboundListResponse;
import intern.rikkei.warehousesystem.dto.outbound.response.OutboundResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OutboundService {
    Page<OutboundListResponse> findAll(OutboundSearchRequest request, Pageable pageable);
    OutboundResponse create(OutboundRequest request);
    OutboundDetailResponse findById(Long id);
    OutboundResponse update(Long id, UpdateOutboundRequest request);
    void delete(Long id);

}
