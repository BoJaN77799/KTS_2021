package com.app.RestaurantApp.reports;

import com.app.RestaurantApp.bonus.Bonus;
import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.order.OrderService;
import com.app.RestaurantApp.orderItem.OrderItem;
import com.app.RestaurantApp.reports.dto.IncomeExpenses;
import com.app.RestaurantApp.reports.dto.Sales;
import com.app.RestaurantApp.salary.Salary;
import com.app.RestaurantApp.users.employee.Employee;
import com.app.RestaurantApp.users.employee.EmployeeRepository;
import com.app.RestaurantApp.users.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportsServiceImpl implements ReportsService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private EmployeeService employeeService;

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
        List<Order> orders = orderService.findAllOrderInIntervalOfDates(dateFrom, dateTo);

        Map<Long, Sales> maps = new HashMap<>();
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

    @Override
    public IncomeExpenses getIncomeExpenses(String indikator) {
        long dateFrom = generateDateFrom(indikator);
        long dateTo = System.currentTimeMillis();
        List<Order> orders = orderService.findAllOrderInIntervalOfDates(dateFrom, dateTo);

        IncomeExpenses ie = new IncomeExpenses();
        ie.setIncome(calculateIncome(orders));
        ie.setExpenses(calculateExpenses(dateFrom, dateTo));

        return ie;
    }

    @Override
    public double calculateIncome(List<Order> orders) {
        double sum = 0;
        for (Order o : orders)
            sum += o.getProfit();
        return sum;
    }

    @Override
    public double calculateExpenses(long dateFrom, long dateTo) {
        double sum = 0;
        List<Employee> employees = employeeService.findByDeleted(false);

        for (Employee e : employees) {
            for (Bonus b : e.getBonuses())
                if (b.getDate() >= dateFrom && b.getDate() <= dateTo)
                    sum += b.getAmount();

            for (Salary s : e.getSalaries())
                if (s.getDateFrom() >= dateFrom && s.getDateFrom() <= dateTo)
                    sum += s.getAmount();
        }

        return sum;
    }
}
