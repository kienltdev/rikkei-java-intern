package intern.rikkei.warehousesystem.service.impl;

import intern.rikkei.warehousesystem.dto.request.InboundRequest;
import intern.rikkei.warehousesystem.dto.request.InboundSearchRequest;
import intern.rikkei.warehousesystem.dto.request.UpdateInboundRequest;
import intern.rikkei.warehousesystem.dto.response.ImportResultResponse;
import intern.rikkei.warehousesystem.dto.response.InboundResponse;
import intern.rikkei.warehousesystem.entity.Inbound;
import intern.rikkei.warehousesystem.enums.InboundStatus;
import intern.rikkei.warehousesystem.enums.ProductType;
import intern.rikkei.warehousesystem.enums.SupplierCode;
import intern.rikkei.warehousesystem.exception.InvalidOperationException;
import intern.rikkei.warehousesystem.exception.ResourceNotFoundException;
import intern.rikkei.warehousesystem.mapper.InboundMapper;
import intern.rikkei.warehousesystem.repository.InboundRepository;
import intern.rikkei.warehousesystem.repository.specification.InboundSpecification;
import intern.rikkei.warehousesystem.service.InboundImportService;
import intern.rikkei.warehousesystem.service.InboundService;
import intern.rikkei.warehousesystem.service.parser.FileParserStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InboundServiceImpl implements InboundService {

    private final InboundRepository inboundRepository;
    private final InboundMapper inboundMapper;
    private final MessageSource messageSource;
    private final List<FileParserStrategy> parsers;
    private final InboundImportService inboundImportService;

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

        ProductType productType = StringUtils.hasText(request.getProductType()) ?
                ProductType.valueOf(request.getProductType().toUpperCase()) : null;

        SupplierCode supplierCode = StringUtils.hasText(request.getSupplierCd()) ?
                SupplierCode.fromCode(request.getSupplierCd().toUpperCase()) : null;

        Specification<Inbound> spec = InboundSpecification.filterBy(
                productType,
                supplierCode
        );

        Page<Inbound> inboundPage = inboundRepository.findAll(spec, pageable);

        return inboundPage.map(inboundMapper::toInboundResponse);
    }

    @Override
    @Transactional
    public InboundResponse updateInbound(Long id, UpdateInboundRequest request) {
        Inbound existingInbound = inboundRepository.findById(id)
                .orElseThrow(() -> {
                    String message = messageSource.getMessage("error.inbound.notFound", new Object[]{id}, LocaleContextHolder.getLocale());
                    return new ResourceNotFoundException("INBOUND_NOT_FOUND", message);
                });

        if (existingInbound.getStatus() != InboundStatus.NOT_OUTBOUND) {
            String message = messageSource.getMessage("error.inbound.updateNotAllowed", new Object[]{existingInbound.getStatus().getName()}, LocaleContextHolder.getLocale());
            throw new InvalidOperationException("UPDATE_NOT_ALLOWED", message);
        }

        inboundMapper.updateInboundFromRequest(request, existingInbound);
        Inbound savedInbound = inboundRepository.save(existingInbound);

        return inboundMapper.toInboundResponse(savedInbound);
    }


    @Override
    public ImportResultResponse importFromExcel(MultipartFile file) {
        return inboundImportService.importInbounds(file);
    }}