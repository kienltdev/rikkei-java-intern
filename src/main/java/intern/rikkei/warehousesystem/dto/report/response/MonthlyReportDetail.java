package intern.rikkei.warehousesystem.dto.report.response;

public record MonthlyReportDetail(
        int month,
        long beginningBalance,
        long inboundQuantity,
        long outboundQuantity,
        long endingBalance,
        long difference
) {
}
