package com.app.RestaurantApp.reports;

import com.app.RestaurantApp.reports.dto.Sales;

import java.util.List;

public interface ReportsService {

    long generateDateFrom(String reportParameter);

    List<Sales> getReportsSales(String indikator);
}
