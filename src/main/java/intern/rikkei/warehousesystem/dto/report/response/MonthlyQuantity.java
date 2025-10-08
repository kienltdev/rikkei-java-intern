package intern.rikkei.warehousesystem.dto.report.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Internal DTO used for aggregating monthly quantities from the database. Not directly exposed in API responses.",
        hidden = true
)
public record MonthlyQuantity(
        int month,
        long quantity
) {
}
