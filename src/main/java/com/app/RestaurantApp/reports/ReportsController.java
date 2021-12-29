package com.app.RestaurantApp.reports;

import com.app.RestaurantApp.reports.dto.IncomeExpenses;
import com.app.RestaurantApp.reports.dto.Sales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import com.app.RestaurantApp.reports.dto.UserReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("api/reports")
public class ReportsController {

    @Autowired
    private ReportsService reportsService;

    @GetMapping(value = "/getReportsSales/{indicator}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER')")
    public List<Sales> getReportsSales(@PathVariable String indicator) {
        long dateFrom = reportsService.generateDateFrom(indicator);
        long dateTo = System.currentTimeMillis();
        return reportsService.getReportsSales(dateFrom, dateTo);
    }

    @GetMapping(value = "/getIncomeExpenses/{indicator}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER')")
    public IncomeExpenses getIncomeExpenses(@PathVariable String indicator) {
        long dateFrom = reportsService.generateDateFrom(indicator);
        long dateTo = System.currentTimeMillis();
        return reportsService.getIncomeExpenses(dateFrom, dateTo);
    }

    @GetMapping(value = "/activity/{dateFrom}-{dateTo}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<UserReportDTO>> getActivityReport(@PathVariable String dateFrom, @PathVariable String dateTo) {
        long dateFromL = LocalDate.parse(dateFrom, DateTimeFormatter.ofPattern("dd.MM.yyyy."))
                .atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
        long dateToL = LocalDate.parse(dateTo, DateTimeFormatter.ofPattern("dd.MM.yyyy."))
                .atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
        List<UserReportDTO> users = reportsService.activityReport(dateFromL, dateToL);
        if(users.isEmpty())
            return new ResponseEntity<>(users, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
