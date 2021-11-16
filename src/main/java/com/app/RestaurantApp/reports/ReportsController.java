package com.app.RestaurantApp.reports;

import com.app.RestaurantApp.reports.dto.UserReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(value = "/activity")
    public ResponseEntity<List<UserReportDTO>> getActivityReport(@PathParam("reportParameter") String reportParameter) {
        long dateFrom = reportsService.generateDateFrom(reportParameter);
        List<UserReportDTO> users = reportsService.activityReport(dateFrom, System.currentTimeMillis());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
