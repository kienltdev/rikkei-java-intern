package intern.rikkei.warehousesystem.service.impl;

import intern.rikkei.warehousesystem.dto.response.MonthlyInventoryReportResponse;
import intern.rikkei.warehousesystem.dto.response.MonthlyQuantity;
import intern.rikkei.warehousesystem.dto.response.MonthlyReportDetail;
import intern.rikkei.warehousesystem.repository.InboundRepository;
import intern.rikkei.warehousesystem.repository.OutboundRepository;
import intern.rikkei.warehousesystem.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final InboundRepository inboundRepository;
    private final OutboundRepository outboundRepository;

    @Override
    @Transactional(readOnly = true)
    public MonthlyInventoryReportResponse getMonthlyInventoryReport(int year) {
        List<MonthlyReportDetail> monthlyReports = new ArrayList<>();
        LocalDate firstDayOfYear = LocalDate.of(year, Month.JANUARY, 1);
        long totalInboundBeforeYear = inboundRepository.sumQuantityByReceiveDateBefore(firstDayOfYear);
        long totalOutboundBeforeYear = outboundRepository.sumQuantityByShippingDateBefore(firstDayOfYear);
        long currentBeginningBalance = totalInboundBeforeYear - totalOutboundBeforeYear;

        Map<Integer, Long> monthlyInbounds = inboundRepository.findMonthlySummariesByYear(year).stream()
                .collect(Collectors.toMap(MonthlyQuantity::month, MonthlyQuantity::quantity));
        Map<Integer, Long> monthlyOutbounds = outboundRepository.findMonthlySummariesByYear(year).stream()
                .collect(Collectors.toMap(MonthlyQuantity::month, MonthlyQuantity::quantity));

        for (int month = 1; month <= 12; month++) {
            YearMonth  yearMonth = YearMonth.of(year, month);
            LocalDate startDate = yearMonth.atDay(1);
            LocalDate endDate = yearMonth.atEndOfMonth();
            long inboundThisMonth = monthlyInbounds.getOrDefault(month, 0L);
            long outboundThisMonth =  monthlyOutbounds.getOrDefault(month, 0L);
            long endingBalance = currentBeginningBalance + inboundThisMonth - outboundThisMonth;
            long difference = inboundThisMonth - outboundThisMonth;

            MonthlyReportDetail detail = new MonthlyReportDetail(
                    month,
                    currentBeginningBalance,
                    inboundThisMonth,
                    outboundThisMonth,
                    endingBalance,
                    difference
            );
            monthlyReports.add(detail);
            currentBeginningBalance = endingBalance;
        }
        return new MonthlyInventoryReportResponse(year, monthlyReports);
    }
}
