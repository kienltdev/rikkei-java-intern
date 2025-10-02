package intern.rikkei.warehousesystem.controller;

import intern.rikkei.warehousesystem.dto.response.MonthlyInventoryReportResponse;
import intern.rikkei.warehousesystem.exception.InvalidOperationException;
import intern.rikkei.warehousesystem.service.ReportService;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
public class ReportController {
    private final ReportService reportService;
    private final MessageSource messageSource;

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
