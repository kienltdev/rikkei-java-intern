package intern.rikkei.warehousesystem.service;

import intern.rikkei.warehousesystem.dto.report.response.MonthlyInventoryReportResponse;

public interface ReportService {
    MonthlyInventoryReportResponse getMonthlyInventoryReport(int year);
}
