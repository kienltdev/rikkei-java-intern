package intern.rikkei.warehousesystem.service;

import intern.rikkei.warehousesystem.dto.request.OutboundRequest;
import intern.rikkei.warehousesystem.dto.request.OutboundSearchRequest;
import intern.rikkei.warehousesystem.dto.request.UpdateOutboundRequest;
import intern.rikkei.warehousesystem.dto.response.OutboundDetailResponse;
import intern.rikkei.warehousesystem.dto.response.OutboundResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OutboundService {
    Page<OutboundResponse> findAll(OutboundSearchRequest request, Pageable pageable);
    OutboundResponse create(OutboundRequest request);
    OutboundDetailResponse findById(Long id);
    OutboundResponse update(Long id, UpdateOutboundRequest request);
    void delete(Long id);

}
