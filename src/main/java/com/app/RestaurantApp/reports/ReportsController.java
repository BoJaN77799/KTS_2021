package com.app.RestaurantApp.reports;

import com.app.RestaurantApp.reports.dto.Sales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/reports")
public class ReportsController {

    @Autowired
    private ReportsService reportsService;

    @GetMapping(value = "/getReportsSales/{indikator}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Sales> getReportsSales(@PathVariable String indikator) {
        return reportsService.getReportsSales(indikator);
    }
}