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

        // Gọi phương thức detectDelimiter đã được cải tiến
        char delimiter = detectDelimiter(file);

        CSVFormat customFormat = CSVFormat.DEFAULT.builder()
                .setDelimiter(delimiter)
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreHeaderCase(true)
                .setTrim(true)
                .build();

        try (
                InputStream bomInputStream = BOMInputStream.builder()
                        .setInputStream(file.getInputStream())
                        .get();
                BufferedReader reader = new BufferedReader(new InputStreamReader(bomInputStream, StandardCharsets.UTF_8));
                CSVParser csvParser = new CSVParser(reader, customFormat)
        ) {
            for (CSVRecord csvRecord : csvParser) {
                if (csvRecord.isConsistent() && !isRecordEmpty(csvRecord)) {
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
        }
        return dataList;
    }

    @Override
    public boolean supports(String contentType) {
        return "text/csv".equals(contentType)
                || "application/csv".equals(contentType)
                || "application/vnd.ms-excel".equals(contentType);
    }


    private char detectDelimiter(MultipartFile file) throws IOException {
        try (
                InputStream bomInputStream = BOMInputStream.builder()
                        .setInputStream(file.getInputStream())
                        .get();
                BufferedReader reader = new BufferedReader(new InputStreamReader(bomInputStream, StandardCharsets.UTF_8))
        ) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
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