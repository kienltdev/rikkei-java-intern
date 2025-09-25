package intern.rikkei.warehousesystem.service.impl;

import intern.rikkei.warehousesystem.dto.request.OutboundRequest;
import intern.rikkei.warehousesystem.dto.request.OutboundSearchRequest;
import intern.rikkei.warehousesystem.dto.response.OutboundDetailResponse;
import intern.rikkei.warehousesystem.dto.response.OutboundResponse;
import intern.rikkei.warehousesystem.entity.Inbound;
import intern.rikkei.warehousesystem.entity.Outbound;
import intern.rikkei.warehousesystem.enums.InboundStatus;
import intern.rikkei.warehousesystem.enums.ShippingMethod;
import intern.rikkei.warehousesystem.exception.InvalidOperationException;
import intern.rikkei.warehousesystem.exception.ResourceNotFoundException;
import intern.rikkei.warehousesystem.mapper.OutboundMapper;
import intern.rikkei.warehousesystem.repository.InboundRepository;
import intern.rikkei.warehousesystem.repository.OutboundRepository;
import intern.rikkei.warehousesystem.service.OutboundService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OutboundServiceImpl implements OutboundService {
    private final OutboundRepository outboundRepository;
    private final OutboundMapper outboundMapper;
    private final InboundRepository inboundRepository;
    private final MessageSource messageSource;

    @Override
    @Transactional(readOnly = true)
    public Page<OutboundResponse> findAll(OutboundSearchRequest request, Pageable pageable){
        Page<Outbound>  outboundPage = outboundRepository.findAll(pageable);
        return outboundPage.map(outboundMapper::toOutboundResponse);
    }

    @Override
    @Transactional
    public OutboundResponse create(OutboundRequest request){
        Inbound inbound = inboundRepository.findById(request.inbId())
                .orElseThrow(() -> {
                    String message = messageSource.getMessage("error.inbound.notFound", new Object[]{request.inbId()},
                            LocaleContextHolder.getLocale());
                    return new ResourceNotFoundException("INBOUND_NOT_FOUND", message);
                });

        Integer totalShipped = outboundRepository.sumQuantityByInboundId(inbound.getId());
        int availableQuantity = inbound.getQuantity() - totalShipped;
        Integer reqQuantity = request.quantity();
        if(reqQuantity != null && reqQuantity > availableQuantity){
            String message = messageSource.getMessage("error.outbound.insufficientQuantity",
                    new Object[]{inbound.getId(), availableQuantity, request.quantity()},
                    LocaleContextHolder.getLocale());
            throw new InvalidOperationException("INSUFFICIENT_QUANTITY", message);
        }

        Outbound newOutbound = new Outbound();
        newOutbound.setInbound(inbound);
        newOutbound.setQuantity(request.quantity());
        newOutbound.setShippingDate(request.shippingDate());
        newOutbound.setShippingMethod(ShippingMethod.fromCode(request.shippingMethod()));
        Outbound savedOutbound = outboundRepository.save(newOutbound);

        if(reqQuantity != null) {
            int newTotalShipped = totalShipped + reqQuantity;
            if(newTotalShipped >= inbound.getQuantity()){
                inbound.setStatus(InboundStatus.FULLY_OUTBOUND);
            } else {
                inbound.setStatus(InboundStatus.PARTIALLY_OUTBOUND);
            }
            inboundRepository.save(inbound);

        }
        return outboundMapper.toOutboundResponse(savedOutbound);
    }

    @Override
    @Transactional(readOnly = true)
    public OutboundDetailResponse findById(Long id) {
        Outbound outbound = outboundRepository.findById(id)
                .orElseThrow(() -> {
                    String message = messageSource.getMessage("error.outbound.notFound", new Object[]{id},
                            LocaleContextHolder.getLocale());
                    return new ResourceNotFoundException("OUTBOUND_NOT_FOUND", message);
                });
        Inbound inbound = outbound.getInbound();
        Integer totalShipped = outboundRepository.sumQuantityByInboundId(inbound.getId());
        int availableQuantity = inbound.getQuantity() - totalShipped;

       return outboundMapper.toOutboundDetailResponse(outbound, availableQuantity);


    }
}
