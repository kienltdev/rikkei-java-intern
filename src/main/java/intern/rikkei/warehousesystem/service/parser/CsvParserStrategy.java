package intern.rikkei.warehousesystem.service.parser;


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

        char delimiter = detectDelimiter(file);

        CSVFormat customFormat = CSVFormat.DEFAULT.builder()
                .setDelimiter(delimiter)
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreHeaderCase(true)
                .setTrim(true)
                .build();

        try (
                // Sử dụng cách khởi tạo mới, không bị deprecated
                InputStream bomInputStream = BOMInputStream.builder()
                        .setInputStream(file.getInputStream())
                        .get();
                BufferedReader reader = new BufferedReader(new InputStreamReader(bomInputStream, StandardCharsets.UTF_8));
                CSVParser csvParser = new CSVParser(reader, customFormat)
        ) {
            for (CSVRecord csvRecord : csvParser) {
                InboundData data = new InboundData(
                        csvRecord.get("Supplier Country"),
                        csvRecord.get("Invoice"),
                        csvRecord.get("Product type"),
                        csvRecord.get("Quantity"),
                        csvRecord.get("Receive date")
                );
                dataList.add(data);
            }
        }
        return dataList;
    }

    @Override
    public boolean supports(String contentType) {
        return "text/csv".equals(contentType) || "application/csv".equals(contentType);
    }

    /**
     * Helper method to detect the delimiter (',' or ';') by reading the first line of the file.
     * @param file The uploaded multipart file.
     * @return The detected delimiter character.
     * @throws IOException if an I/O error occurs.
     */
    private char detectDelimiter(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                return ',';
            }
            long commaCount = headerLine.chars().filter(ch -> ch == ',').count();
            long semicolonCount = headerLine.chars().filter(ch -> ch == ';').count();

            return semicolonCount > commaCount ? ';' : ',';
        }
    }
}