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
import java.util.List;

@RestController
@RequestMapping("api/reports")
public class ReportsController {

    @Autowired
    private ReportsService reportsService;

    @GetMapping(value = "/getReportsSales/{indikator}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER')")
    public List<Sales> getReportsSales(@PathVariable String indikator) {
        return reportsService.getReportsSales(indikator);
    }

    @GetMapping(value = "/getIncomeExpenses/{indikator}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('MANAGER')")
    public IncomeExpenses getIncomeExpenses(@PathVariable String indikator) {
        return reportsService.getIncomeExpenses(indikator);
    }

    @GetMapping(value = "/activity")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<UserReportDTO>> getActivityReport(@PathParam("reportParameter") String reportParameter) {
        long dateFrom = reportsService.generateDateFrom(reportParameter);
        List<UserReportDTO> users = reportsService.activityReport(dateFrom, System.currentTimeMillis());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}