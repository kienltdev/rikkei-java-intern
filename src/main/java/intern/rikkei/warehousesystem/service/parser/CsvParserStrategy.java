package intern.rikkei.warehousesystem.service.parser;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvParserStrategy implements FileParserStrategy {

    @Override
    public List<InboundData> parse(MultipartFile file) throws IOException {
        List<InboundData> dataList = new ArrayList<>();

        // Gọi phương thức detectDelimiter đã được cải tiến
        char delimiter = detectDelimiter(file);

        CSVFormat customFormat = CSVFormat.DEFAULT.builder()
                .setDelimiter(delimiter)
                .setHeader() // Tự động đọc dòng đầu tiên làm header
                .setSkipHeaderRecord(true) // Bỏ qua dòng header khi lặp qua các record
                .setIgnoreHeaderCase(true) // Không phân biệt hoa thường của header
                .setTrim(true) // Cắt bỏ khoảng trắng ở đầu và cuối giá trị
                .build();

        // Sử dụng try-with-resources để đảm bảo stream được đóng đúng cách
        try (
                // Luôn sử dụng BOMInputStream để xử lý BOM một cách an toàn
                InputStream bomInputStream = BOMInputStream.builder()
                        .setInputStream(file.getInputStream())
                        .get();
                BufferedReader reader = new BufferedReader(new InputStreamReader(bomInputStream, StandardCharsets.UTF_8));
                CSVParser csvParser = new CSVParser(reader, customFormat)
        ) {
            for (CSVRecord csvRecord : csvParser) {
                // Kiểm tra để đảm bảo record không phải là một dòng trống
                if (csvRecord.isConsistent() && !isRecordEmpty(csvRecord)) {
                    InboundData data = new InboundData(
                            csvRecord.get("Supplier Country"),
                            csvRecord.get("Invoice"),
                            csvRecord.get("Product type"),
                            csvRecord.get("Quantity"),
                            csvRecord.get("Receive date")
                    );

                    String rawInvoice = csvRecord.get("Invoice");
                    dataList.add(data);
                }
            }
        }
        return dataList;
    }

    @Override
    public boolean supports(String contentType) {
        // Hỗ trợ cả content type chuẩn và một số content type phổ biến khác mà trình duyệt có thể gửi
        return "text/csv".equals(contentType)
                || "application/csv".equals(contentType)
                || "application/vnd.ms-excel".equals(contentType);
    }


    /**
     * Helper method to detect the delimiter (',' or ';') by reading the first line of the file.
     * @param file The uploaded multipart file.
     * @return The detected delimiter character.
     * @throws IOException if an I/O error occurs.
     */
    private char detectDelimiter(MultipartFile file) throws IOException {
        // Sử dụng try-with-resources và BOMInputStream
        try (
                InputStream bomInputStream = BOMInputStream.builder()
                        .setInputStream(file.getInputStream())
                        .get();
                BufferedReader reader = new BufferedReader(new InputStreamReader(bomInputStream, StandardCharsets.UTF_8))
        ) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                // Mặc định là dấu phẩy nếu file trống
                return ',';
            }
            long commaCount = headerLine.chars().filter(ch -> ch == ',').count();
            long semicolonCount = headerLine.chars().filter(ch -> ch == ';').count();

            return semicolonCount > commaCount ? ';' : ',';
        }
    }

    private boolean isRecordEmpty(CSVRecord record) {
        for (String value : record) {
            if (value != null && !value.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }
}