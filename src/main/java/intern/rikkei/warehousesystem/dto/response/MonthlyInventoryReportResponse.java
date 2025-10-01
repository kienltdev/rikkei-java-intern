package intern.rikkei.warehousesystem.dto.response;

import java.util.List;

public record MonthlyInventoryReportResponse(
        int year,
        List<MonthlyReportDetail> monthlyReports
) {
}
