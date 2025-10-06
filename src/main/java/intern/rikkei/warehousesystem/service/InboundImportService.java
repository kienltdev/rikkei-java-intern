package intern.rikkei.warehousesystem.service;

import intern.rikkei.warehousesystem.dto.inbound.response.ImportResultResponse;
import org.springframework.web.multipart.MultipartFile;

public interface InboundImportService {
    ImportResultResponse importInbounds(MultipartFile file);
}
