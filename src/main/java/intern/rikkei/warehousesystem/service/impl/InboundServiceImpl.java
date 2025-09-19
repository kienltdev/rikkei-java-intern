package intern.rikkei.warehousesystem.service.impl;

import intern.rikkei.warehousesystem.dto.request.InboundRequest;
import intern.rikkei.warehousesystem.dto.request.InboundSearchRequest;
import intern.rikkei.warehousesystem.dto.request.UpdateInboundRequest;
import intern.rikkei.warehousesystem.dto.response.InboundResponse;
import intern.rikkei.warehousesystem.entity.Inbound;
import intern.rikkei.warehousesystem.enums.InboundStatus;
import intern.rikkei.warehousesystem.exception.InvalidOperationException;
import intern.rikkei.warehousesystem.exception.ResourceNotFoundException;
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
    public Page<InboundResponse> findAll(InboundSearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        Specification<Inbound> spec = InboundSpecification.filterBy(
                request.getProductType(),
                request.getSupplierCd()
        );

        Page<Inbound> inboundPage = inboundRepository.findAll(spec, pageable);

        return inboundPage.map(inboundMapper::toInboundResponse);
    }

    @Override
    @Transactional
    public InboundResponse updateInbound(Long id, UpdateInboundRequest request) {
        Inbound existingInbound = inboundRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("INBOUND_NOT_FOUND", "Inbound not found with id: " + id));

        if(existingInbound.getStatus() != InboundStatus.NOT_OUTBOUND) {
            throw new InvalidOperationException(
                    "UPDATE_NOT_ALLOWED",
                    "Inbound cannot be updated because it has already been linked to an outbound");
        }

        inboundMapper.updateInboundFromRequest(request, existingInbound);
        Inbound savedInbound = inboundRepository.save(existingInbound);

        return inboundMapper.toInboundResponse(savedInbound);
    }
}