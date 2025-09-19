package intern.rikkei.warehousesystem.service.parser;

public record InboundData(
        String supplierCd,
        String invoice,
        String productType,
        String quantity,
        String receiveDate
) {
}
