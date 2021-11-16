package com.app.RestaurantApp.reports;

import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.order.OrderService;
import com.app.RestaurantApp.orderItem.OrderItem;
import com.app.RestaurantApp.reports.dto.Sales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportsServiceImpl implements ReportsService {

    @Autowired
    private OrderService orderService;

    @Override
    public long generateDateFrom(String reportParameter) {
        LocalDate dateTo = LocalDate.now();
        switch (reportParameter.toLowerCase()) {
            case "monthly": {
                return dateTo.minusMonths(1).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
            }
            case "quarterly": {
                return dateTo.minusMonths(3).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
            }
            case "yearly": {
                return dateTo.minusYears(1).atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
            }
            default:
                return 0;
        }
    }

    @Override
    public List<Sales> getReportsSales(String indikator) {
        long dateFrom = generateDateFrom(indikator);
        long dateTo = System.currentTimeMillis();

        Map<Long, Sales> maps = new HashMap<>();
        List<Order> orders = orderService.findAllOrderInIntervalOfDates(dateFrom, dateTo);
        for (Order o : orders) {
            for (OrderItem oi : o.getOrderItems()){
                if (maps.containsKey(oi.getItem().getId())) {
                    int currentItemCount = maps.get(oi.getItem().getId()).getItemCount() + oi.getQuantity();
                    double currentItemPrice = maps.get(oi.getItem().getId()).getPriceCount() + oi.getPrice() * oi.getQuantity();
                    maps.get(oi.getItem().getId())
                            .setItemCount(currentItemCount);
                    maps.get(oi.getItem().getId())
                            .setPriceCount(currentItemPrice);
                } else {
                    maps.put(oi.getItem().getId(),
                            new Sales(oi.getItem().getName(),
                                    oi.getPrice() * oi.getQuantity(), oi.getQuantity()));
                }
            }
        }
        return maps.values().stream().toList();
    }
}
