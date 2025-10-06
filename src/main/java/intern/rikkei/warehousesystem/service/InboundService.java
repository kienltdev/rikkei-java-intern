package intern.rikkei.warehousesystem.service;

import intern.rikkei.warehousesystem.dto.inbound.request.InboundRequest;
import intern.rikkei.warehousesystem.dto.inbound.request.InboundSearchRequest;
import intern.rikkei.warehousesystem.dto.inbound.request.InboundStatisticsRequest;
import intern.rikkei.warehousesystem.dto.inbound.request.UpdateInboundRequest;
import intern.rikkei.warehousesystem.dto.inbound.response.ImportResultResponse;
import intern.rikkei.warehousesystem.dto.inbound.response.InboundDetailResponse;
import intern.rikkei.warehousesystem.dto.inbound.response.InboundResponse;
import intern.rikkei.warehousesystem.dto.inbound.response.PaginatedInboundStatisticsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface InboundService {
    InboundResponse createInbound(InboundRequest request);
    Page<InboundResponse> findAll(InboundSearchRequest request, Pageable pageable);
    InboundResponse updateInbound(Long id, UpdateInboundRequest request);
    ImportResultResponse importFromFile(MultipartFile file);
    void deleteInbound(Long id);
    PaginatedInboundStatisticsResponse getInboundStatistics(InboundStatisticsRequest request, Pageable pageable);
    InboundDetailResponse findInboundDetailById(Long id);
}
