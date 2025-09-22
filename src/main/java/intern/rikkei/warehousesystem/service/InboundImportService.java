package intern.rikkei.warehousesystem.service;

import intern.rikkei.warehousesystem.dto.response.ImportResultResponse;
import org.springframework.web.multipart.MultipartFile;

public interface InboundImportService {
    ImportResultResponse importInbounds(MultipartFile file);
}
