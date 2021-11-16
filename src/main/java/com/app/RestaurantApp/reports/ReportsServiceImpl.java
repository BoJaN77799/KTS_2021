package com.app.RestaurantApp.reports;

import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.order.OrderService;
import com.app.RestaurantApp.reports.dto.UserReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    public List<UserReportDTO> activityReport(long dateFrom, long dateTo) {
        List<Order> orders = orderService.getOrdersByDate(dateFrom, dateTo);
        HashMap<Long, UserReportDTO> occurrenceMap = new HashMap<>();
        orders.forEach((order -> {
            Long barmenId = order.getBarman() != null ? order.getBarman().getId() : -1;
            Long cookId = order.getCook() != null ? order.getCook().getId() : -1;
            Long waiterId = order.getWaiter().getId();
            if(barmenId != -1)
                if(!occurrenceMap.containsKey(barmenId))
                    occurrenceMap.put(barmenId, new UserReportDTO(barmenId, order.getBarman().getFirstName(), order.getBarman().getLastName(), UserType.BARMAN, 1L));
                else
                    occurrenceMap.get(barmenId).setOrdersAccomplished(occurrenceMap.get(barmenId).getOrdersAccomplished() + 1);
            if(cookId != -1)
                if(!occurrenceMap.containsKey(cookId))
                    occurrenceMap.put(cookId, new UserReportDTO(cookId, order.getCook().getFirstName(), order.getCook().getLastName(), UserType.COOK, 1L));
                else
                    occurrenceMap.get(cookId).setOrdersAccomplished(occurrenceMap.get(cookId).getOrdersAccomplished() + 1);

            if(!occurrenceMap.containsKey(waiterId))
                occurrenceMap.put(waiterId, new UserReportDTO(waiterId, order.getWaiter().getFirstName(), order.getWaiter().getLastName(), UserType.WAITER, 1L));
            else
                occurrenceMap.get(waiterId).setOrdersAccomplished(occurrenceMap.get(waiterId).getOrdersAccomplished() + 1);
        }));
        List<UserReportDTO> userReportDTOS = new ArrayList<>();
        occurrenceMap.forEach((key, value) -> userReportDTOS.add(value));
        return userReportDTOS;
    }
}
