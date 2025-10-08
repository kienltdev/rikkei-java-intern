package intern.rikkei.warehousesystem.dto.report.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Represents the complete monthly inventory report for a given year.")
public record MonthlyInventoryReportResponse(
        @Schema(description = "The year the report was generated for.", example = "2024")
        int year,
        @Schema(description = "A list containing the detailed report for each of the 12 months.")
        List<MonthlyReportDetail> monthlyReports
) {
}
