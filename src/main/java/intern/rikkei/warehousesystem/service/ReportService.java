package intern.rikkei.warehousesystem.service;

import intern.rikkei.warehousesystem.dto.response.MonthlyInventoryReportResponse;

public interface ReportService {
    MonthlyInventoryReportResponse getMonthlyInventoryReport(int year);
}
