package intern.rikkei.warehousesystem.service.impl;

import intern.rikkei.warehousesystem.dto.request.InboundRequest;
import intern.rikkei.warehousesystem.dto.response.InboundResponse;
import intern.rikkei.warehousesystem.entity.Inbound;
import intern.rikkei.warehousesystem.enums.InboundStatus;
import intern.rikkei.warehousesystem.mapper.InboundMapper;
import intern.rikkei.warehousesystem.repository.InboundRepository;
import intern.rikkei.warehousesystem.repository.specification.InboundSpecification;
import intern.rikkei.warehousesystem.service.InboundService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InboundServiceImpl implements InboundService {

    private final InboundRepository inboundRepository;
    private final InboundMapper inboundMapper;
    private final MessageSource messageSource;

    @Override
    @Transactional
    public InboundResponse createInbound(InboundRequest request) {

        Inbound inbound = inboundMapper.toInbound(request);

        inbound.setStatus(InboundStatus.NOT_OUTBOUND);

        Inbound savedInbound = inboundRepository.save(inbound);

        return inboundMapper.toInboundResponse(savedInbound);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InboundResponse> findAll(int page, int size, String productType, String supplierCd) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<Inbound> spec = InboundSpecification.filterBy(productType, supplierCd);

        Page<Inbound> inboundPage = inboundRepository.findAll(spec, pageable);

        return inboundPage.map(inboundMapper::toInboundResponse);
    }
}