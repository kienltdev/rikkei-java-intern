package intern.rikkei.warehousesystem.controller;

import intern.rikkei.warehousesystem.dto.report.response.MonthlyInventoryReportResponse;
import intern.rikkei.warehousesystem.exception.ApiErrorResponse;
import intern.rikkei.warehousesystem.exception.InvalidOperationException;
import intern.rikkei.warehousesystem.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Year;

@RestController
@RequestMapping("/api/warehouse/reports")
@RequiredArgsConstructor
@Validated
@Tag(name = "Reporting", description = "APIs for generating reports (Admin only)")
public class ReportController {
    private final ReportService reportService;
    private final MessageSource messageSource;

    @Operation(summary = "Get monthly inventory report", description = "Generates a year-long monthly inventory report, detailing beginning balance, inbound, outbound, and ending balance for each month. Requires ADMIN role.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Report generated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MonthlyInventoryReportResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid year provided (e.g., in the future)",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden - User is not an ADMIN",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    @GetMapping("/monthly-inventory")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MonthlyInventoryReportResponse> getMonthlyInventoryReport(
            @RequestParam
            @NotNull(message = "{validation.year.notNull}")
            @Min(value = 1970, message = "{validation.year.min}")
            Integer year
    ) {
        if(year > Year.now().getValue()) {
            String message = messageSource.getMessage("validation.year.future",
                    new Object[]{year},
                    LocaleContextHolder.getLocale());
            throw new InvalidOperationException("INVALID_YEAR", message);
        }

        MonthlyInventoryReportResponse response = reportService.getMonthlyInventoryReport(year);
        return ResponseEntity.ok(response);
    }
}
