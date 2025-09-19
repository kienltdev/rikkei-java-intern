package intern.rikkei.warehousesystem.service.parser;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileParserStrategy {
    List<InboundData> parse(MultipartFile file) throws IOException;
    boolean supports(String contentType);
}
