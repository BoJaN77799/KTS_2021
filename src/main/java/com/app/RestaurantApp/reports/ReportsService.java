package com.app.RestaurantApp.reports;

import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.reports.dto.IncomeExpenses;
import com.app.RestaurantApp.reports.dto.Sales;
import com.app.RestaurantApp.reports.dto.UserReportDTO;
import org.springframework.data.util.Pair;

import java.util.List;

public interface ReportsService {

    long generateDateFrom(String reportParameter);

    List<Sales> getReportsSales(long dateFrom, long dateTo);

    IncomeExpenses getIncomeExpenses(long dateFrom, long dateTo);

    double calculateIncome(List<Order> orders);

    double calculateExpenses(long dateFrom, long dateTo);

    List<UserReportDTO> activityReport(long dateFrom, long currentTimeMillis);

}
