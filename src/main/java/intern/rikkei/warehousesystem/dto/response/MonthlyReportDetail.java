package intern.rikkei.warehousesystem.dto.response;

public record MonthlyReportDetail(
        int month,
        long beginningBalance,
        long inboundQuantity,
        long outboundQuantity,
        long endingBalance,
        long difference
) {
}
