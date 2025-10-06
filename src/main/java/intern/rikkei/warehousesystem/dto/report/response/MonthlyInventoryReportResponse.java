package intern.rikkei.warehousesystem.dto.report.response;

import java.util.List;

public record MonthlyInventoryReportResponse(
        int year,
        List<MonthlyReportDetail> monthlyReports
) {
}
