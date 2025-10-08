package intern.rikkei.warehousesystem.service.impl;

import intern.rikkei.warehousesystem.dto.outbound.request.OutboundRequest;
import intern.rikkei.warehousesystem.dto.outbound.request.OutboundSearchRequest;
import intern.rikkei.warehousesystem.dto.outbound.request.UpdateOutboundRequest;
import intern.rikkei.warehousesystem.dto.outbound.response.OutboundDetailResponse;
import intern.rikkei.warehousesystem.dto.outbound.response.OutboundResponse;
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

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

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
        Inbound inbound = inboundRepository.findByIdAndLock(request.inbId())
                .orElseThrow(() -> {
                    String message = messageSource.getMessage("error.inbound.notFound", new Object[]{request.inbId()},
                            LocaleContextHolder.getLocale());
                    return new ResourceNotFoundException("INBOUND_NOT_FOUND", message);
                });

        Long totalShipped = outboundRepository.sumQuantityByInboundId(inbound.getId());
        long availableQuantity = inbound.getQuantity() - totalShipped;
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
            long newTotalShipped = totalShipped + reqQuantity;
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
        Long totalShipped = outboundRepository.sumQuantityByInboundId(inbound.getId());
        long availableQuantity = inbound.getQuantity() - totalShipped;

       return outboundMapper.toOutboundDetailResponse(outbound, availableQuantity);


    }

    @Override
    @Transactional
    public OutboundResponse update(Long id, UpdateOutboundRequest request){
        Outbound outbound = outboundRepository.findById(id)
                .orElseThrow(() -> {
                    String message = messageSource.getMessage("error.outbound.notFound", new Object[]{id},
                            LocaleContextHolder.getLocale());
                    return new ResourceNotFoundException("OUTBOUND_NOT_FOUND", message);
                });
        if(outbound.getShippingDate() != null && !LocalDate.now().isBefore(outbound.getShippingDate())){
            String message = messageSource.getMessage("error.outbound.updateNotAllowed", new Object[]{outbound.getShippingDate()},
                            LocaleContextHolder.getLocale());
            throw new InvalidOperationException("OUTBOUND_UPDATE_NOT_ALLOWED", message);
        }

        Integer oldQuantity = Optional.ofNullable(outbound.getQuantity()).orElse(0);
        Integer newQuantity = request.quantity();
        boolean quantityChanged = (newQuantity != null) && !Objects.equals(oldQuantity, newQuantity);
        Inbound inbound = outbound.getInbound();
        if(quantityChanged){
            inboundRepository.findByIdAndLock(inbound.getId())
                    .orElseThrow(() -> {
                        String message = messageSource.getMessage("error.inbound.notFound", new Object[]{inbound.getId()},
                                LocaleContextHolder.getLocale());
                        return new ResourceNotFoundException("INBOUND_NOT_FOUND", message);
                    });
            Long totalShipped = outboundRepository.sumQuantityByInboundId(inbound.getId());
            long availableQuantity = inbound.getQuantity() - (totalShipped - oldQuantity);
                if(newQuantity > availableQuantity){
                String message = messageSource.getMessage("error.outbound.insufficientQuantity", new Object[]{inbound.getId(),
                        availableQuantity, newQuantity}, LocaleContextHolder.getLocale());
                throw new InvalidOperationException("INSUFFICIENT_QUANTITY", message);
            }
        }

        outboundMapper.updateOutboundFromRequest(request, outbound);

        Outbound savedOutbound = outboundRepository.save(outbound);

        if(quantityChanged){
            Integer totalQuantity = Optional.ofNullable(inbound.getQuantity()).orElse(0);
            Long newTotalShipped = outboundRepository.sumQuantityByInboundId(inbound.getId());
            if(newTotalShipped <= 0){
                inbound.setStatus(InboundStatus.NOT_OUTBOUND);
            } else if(newTotalShipped >= totalQuantity){
                inbound.setStatus(InboundStatus.FULLY_OUTBOUND);
            } else {
                inbound.setStatus(InboundStatus.PARTIALLY_OUTBOUND);
            }

            inboundRepository.save(inbound);
        }

        return outboundMapper.toOutboundResponse(savedOutbound);
    }

    @Override
    @Transactional
    public void delete(Long id){
        Outbound outbound = outboundRepository.findById(id)
                .orElseThrow(() -> {
                    String message = messageSource.getMessage("error.outbound.notFound", new Object[]{id},
                            LocaleContextHolder.getLocale());
                    return new ResourceNotFoundException("OUTBOUND_NOT_FOUND", message);
                });
        if(outbound.getShippingDate() != null && !LocalDate.now().isBefore(outbound.getShippingDate())){
            String message = messageSource.getMessage("error.outbound.deleteNotAllowed", new Object[]{outbound.getShippingDate()},
                    LocaleContextHolder.getLocale());
            throw new InvalidOperationException("OUTBOUND_DELETE_NOT_ALLOWED", message);
        }
        Inbound inbound = outbound.getInbound();
        inboundRepository.findByIdAndLock(inbound.getId())
                .orElseThrow(() -> {
                    String message = messageSource.getMessage("error.inbound.notFound", new Object[]{inbound.getId()},
                            LocaleContextHolder.getLocale());
                    return new ResourceNotFoundException("INBOUND_NOT_FOUND", message);
                });
        outboundRepository.delete(outbound);

        Long newTotalShipped = outboundRepository.sumQuantityByInboundId(inbound.getId());

        Integer totalQuantity = Optional.ofNullable(inbound.getQuantity()).orElse(0);

        if(newTotalShipped <= 0){
            inbound.setStatus(InboundStatus.NOT_OUTBOUND);
        } else if(newTotalShipped >= totalQuantity){
            inbound.setStatus(InboundStatus.FULLY_OUTBOUND);
        } else {
            inbound.setStatus(InboundStatus.PARTIALLY_OUTBOUND);
        }

        inboundRepository.save(inbound);

    }
}
