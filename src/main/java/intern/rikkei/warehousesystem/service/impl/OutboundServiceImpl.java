package intern.rikkei.warehousesystem.service.impl;

import intern.rikkei.warehousesystem.dto.request.OutboundSearchRequest;
import intern.rikkei.warehousesystem.dto.response.OutboundResponse;
import intern.rikkei.warehousesystem.entity.Outbound;
import intern.rikkei.warehousesystem.mapper.OutboundMapper;
import intern.rikkei.warehousesystem.repository.OutboundRepository;
import intern.rikkei.warehousesystem.service.OutboundService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OutboundServiceImpl implements OutboundService {
    private final OutboundRepository outboundRepository;
    private final OutboundMapper outboundMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<OutboundResponse> findAll(OutboundSearchRequest request, Pageable pageable){
        Page<Outbound>  outboundPage = outboundRepository.findAll(pageable);
        return outboundPage.map(outboundMapper::toOutboundResponse);
    }
}
