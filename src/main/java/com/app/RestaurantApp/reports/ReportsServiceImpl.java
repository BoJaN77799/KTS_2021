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

import java.time.*;
import java.util.*;

import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.order.OrderService;
import com.app.RestaurantApp.reports.dto.UserReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                            new Sales(oi.getItem().getId(), oi.getItem().getName(),
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
            if (o.getProfit() != null)
                sum += o.getProfit();
        return sum;
    }

    @Override
    public double calculateExpenses(long dateFrom, long dateTo) {
        double sum = 0;
        List<Employee> employees = employeeService.findAllEmployeesWithSalariesAndBonuses(false);

        for (Employee e : employees) {
            Iterator<Bonus> it =  e.getBonuses().stream().sorted(Comparator.comparingLong(Bonus::getDate)).iterator();
            while (it.hasNext()) {
                Bonus b = it.next();
                if (b.getDate() >= dateFrom && b.getDate() <= dateTo)
                    sum += b.getAmount();
            }

            LocalDate dateFromLD = Instant.ofEpochMilli(dateFrom).atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate dateToLD = Instant.ofEpochMilli(dateTo).atZone(ZoneId.systemDefault()).toLocalDate();
            sum += calculateExpensesPerEmployee(dateFromLD, dateToLD, e);

        }

        return sum;
    }

    private double calculateExpensesPerEmployee(LocalDate dateFromLD, LocalDate dateToLD, Employee e) {
        double sum = 0;
        // ide od najvecih ka manjim datumima je sortirano
        List<Salary> l = e.getSalaries().stream().sorted(Comparator.comparingLong(Salary::getDateFrom).reversed()).toList();

        while (!dateFromLD.equals(dateToLD)) {
            for (Salary s : l) {
                boolean indicator = false;
                LocalDate dateOfSalary = Instant.ofEpochMilli(s.getDateFrom()).atZone(ZoneId.systemDefault()).toLocalDate();
                if (dateOfSalary.equals(dateToLD) || dateOfSalary.isBefore(dateToLD)) {
                    switch (dateToLD.getMonth().minLength()) {
                        case 28 -> sum += s.getAmount() / 28;
                        case 29 -> sum += s.getAmount() / 29;
                        case 30 -> sum += s.getAmount() / 30;
                        default -> sum += s.getAmount() / 31;
                    }
                    indicator = true;
                }
                if (indicator)
                    break;
            }
            dateToLD = dateToLD.minusDays(1);
        }
        return sum;
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
