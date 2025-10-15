package intern.rikkei.warehousesystem.dto.report.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detailed inventory metrics for a single month.")
public record MonthlyReportDetail(
        @Schema(description = "The month number (1 for January, 12 for December).", example = "1")
        int month,

        @Schema(description = "The quantity of inventory at the beginning of the month (Ending Balance of the previous month).", example = "1000")
        long beginningBalance,

        @Schema(description = "The total quantity of products received during this month.", example = "500")
        long inboundQuantity,

        @Schema(description = "The total quantity of products shipped during this month.", example = "300")
        long outboundQuantity,

        @Schema(description = "The quantity of inventory at the end of the month (Beginning Balance + Inbound - Outbound).", example = "1200")
        long endingBalance,

        @Schema(description = "The net change in inventory for the month (Inbound - Outbound).", example = "200")
        long difference
) {
}
