package intern.rikkei.warehousesystem.service;

import intern.rikkei.warehousesystem.dto.request.InboundRequest;
import intern.rikkei.warehousesystem.dto.request.InboundSearchRequest;
import intern.rikkei.warehousesystem.dto.request.InboundStatisticsRequest;
import intern.rikkei.warehousesystem.dto.request.UpdateInboundRequest;
import intern.rikkei.warehousesystem.dto.response.ImportResultResponse;
import intern.rikkei.warehousesystem.dto.response.InboundDetailResponse;
import intern.rikkei.warehousesystem.dto.response.InboundResponse;
import intern.rikkei.warehousesystem.dto.response.InboundStatisticsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface InboundService {
    InboundResponse createInbound(InboundRequest request);
    Page<InboundResponse> findAll(InboundSearchRequest request, Pageable pageable);
    InboundResponse updateInbound(Long id, UpdateInboundRequest request);
    ImportResultResponse importFromExcel(MultipartFile file);
    void deleteInbound(Long id);
    Page<InboundStatisticsResponse> getInboundStatistics(InboundStatisticsRequest request, Pageable pageable);
    InboundDetailResponse findInboundDetailById(Long id);
}
