package intern.rikkei.warehousesystem.service.impl;

import intern.rikkei.warehousesystem.dto.request.InboundImportRowDTO;
import intern.rikkei.warehousesystem.dto.response.ImportErrorDetail;
import intern.rikkei.warehousesystem.dto.response.ImportResultResponse;
import intern.rikkei.warehousesystem.entity.Inbound;
import intern.rikkei.warehousesystem.exception.InvalidOperationException;
import intern.rikkei.warehousesystem.mapper.InboundMapper;
import intern.rikkei.warehousesystem.repository.InboundRepository;
import intern.rikkei.warehousesystem.service.InboundImportService;
import intern.rikkei.warehousesystem.service.parser.FileParserStrategy;
import intern.rikkei.warehousesystem.service.parser.InboundData;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class InboundImportServiceImpl implements InboundImportService {

    private final List<FileParserStrategy> parsers;
    private final Validator validator;
    private final InboundMapper inboundMapper;
    private final InboundRepository inboundRepository;
    private final MessageSource messageSource;
    @Override
    @Transactional
    public ImportResultResponse importInbounds(MultipartFile file) {

        FileParserStrategy parser = findParser(file.getContentType());

        List<InboundData> rawDataList = parseFile(file, parser);

        List<Inbound> successfulInbounds = new ArrayList<>();
        List<ImportErrorDetail> errorDetails = new ArrayList<>();

        for (int i = 0; i < rawDataList.size(); i++) {
            int rowNumber = i + 2;
            InboundData rawData = rawDataList.get(i);

            InboundImportRowDTO rowDTO = createDtoFromRawData(rawData);

            Set<ConstraintViolation<InboundImportRowDTO>> violations = validator.validate(rowDTO);

            if (violations.isEmpty()) {
                Inbound inbound = inboundMapper.fromImportDtoToEntity(rowDTO);
                successfulInbounds.add(inbound);
            } else {
                String errorMessage = violations.stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.joining(", "));
                errorDetails.add(new ImportErrorDetail(rowNumber, errorMessage));
            }
        }

        if (!successfulInbounds.isEmpty()) {
            inboundRepository.saveAll(successfulInbounds);
        }

        return ImportResultResponse.builder()
                .totalRows(rawDataList.size())
                .successCount(successfulInbounds.size())
                .failureCount(errorDetails.size())
                .errorDetails(errorDetails)
                .build();
    }

    private FileParserStrategy findParser(String contentType) {
        return parsers.stream()
                .filter(p -> p.supports(contentType))
                .findFirst()
                .orElseThrow(() -> {
                    String message = messageSource.getMessage("error.file.unsupportedType",
                            new Object[]{contentType},
                            LocaleContextHolder.getLocale());
                    return new InvalidOperationException("UNSUPPORTED_FILE_TYPE", message);
                });
    }

    private List<InboundData> parseFile(MultipartFile file, FileParserStrategy parser) {
        try {
            return parser.parse(file);
        } catch (IOException e) {
            throw new InvalidOperationException("FILE_READ_ERROR", "Failed to read the file: " + e.getMessage());
        }
    }

    private InboundImportRowDTO createDtoFromRawData(InboundData rawData) {
        Integer quantity = null;
        if (StringUtils.hasText(rawData.quantity())) {
            try {
                quantity = Integer.parseInt(rawData.quantity());
            } catch (NumberFormatException e) {
                // Để validator xử lý lỗi này, ta có thể không làm gì ở đây
                // hoặc gán một giá trị không hợp lệ để validator bắt
            }
        }
        return new InboundImportRowDTO(
                rawData.supplierCd(),
                rawData.invoice(),
                rawData.productType(),
                quantity,
                rawData.receiveDate()
        );
    }
}