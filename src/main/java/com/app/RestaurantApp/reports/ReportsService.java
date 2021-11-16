package com.app.RestaurantApp.reports;

import com.app.RestaurantApp.reports.dto.UserReportDTO;
import org.springframework.data.util.Pair;

import java.util.List;

public interface ReportsService {


    long generateDateFrom(String reportParameter);

    List<UserReportDTO> activityReport(long dateFrom, long currentTimeMillis);
}
