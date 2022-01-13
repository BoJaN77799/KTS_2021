package com.app.RestaurantApp.reports;

import com.app.RestaurantApp.bonus.Bonus;
import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.order.OrderService;
import com.app.RestaurantApp.orderItem.OrderItem;
import com.app.RestaurantApp.reports.dto.IncomeExpenses;
import com.app.RestaurantApp.reports.dto.PriceItemDTO;
import com.app.RestaurantApp.reports.dto.Sales;
import com.app.RestaurantApp.salary.Salary;
import com.app.RestaurantApp.users.employee.Employee;
import com.app.RestaurantApp.users.employee.EmployeeService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Documented;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.reports.dto.UserReportDTO;

@Service
public class ReportsServiceImpl implements ReportsService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public long generateDateFrom(String indicator) {
        LocalDate dateTo = LocalDate.now();
        switch (indicator.toLowerCase()) {
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
    public List<Sales> getReportsSales(long dateFrom, long dateTo) {
        List<Order> orders = orderService.findAllOrderInIntervalOfDates(dateFrom, dateTo);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.yyyy.");
        List<String> allMonths = new ArrayList<>();

        Map<Long, Sales> maps = new HashMap<>();
        for (Order o : orders) {
            LocalDate dateOrder = Instant.ofEpochMilli(o.getCreatedAt()).atZone(ZoneId.systemDefault()).toLocalDate();
            String formattedDateOrder = dateOrder.format(formatter);
            if (!allMonths.contains(formattedDateOrder))
                allMonths.add(formattedDateOrder);

            for (OrderItem oi : o.getOrderItems()){
                if (maps.containsKey(oi.getItem().getId())) {
                    int currentItemCount = maps.get(oi.getItem().getId()).getItemCount() + oi.getQuantity();
                    double currentItemPrice = maps.get(oi.getItem().getId()).getPriceCount() + oi.getPrice() * oi.getQuantity();
                    maps.get(oi.getItem().getId())
                            .setItemCount(currentItemCount);
                    maps.get(oi.getItem().getId())
                            .setPriceCount(currentItemPrice);

                    Sales s = maps.get(oi.getItem().getId());
                    if (s.getSalesPerMonth().containsKey(formattedDateOrder)) {
                        int currentItemCountInMonth = s.getSalesPerMonth().get(formattedDateOrder).getItemCount() +
                                oi.getQuantity();
                        double currentItemPriceInMonth = s.getSalesPerMonth().get(formattedDateOrder).getPriceCount() +
                                oi.getPrice() * oi.getQuantity();
                        s.getSalesPerMonth().get(formattedDateOrder).setItemCount(currentItemCountInMonth);
                        s.getSalesPerMonth().get(formattedDateOrder).setPriceCount(currentItemPriceInMonth);
                    } else {
                        s.getSalesPerMonth().put(formattedDateOrder,
                                new PriceItemDTO(oi.getPrice() * oi.getQuantity(), oi.getQuantity()));
                    }
                } else {
                    maps.put(oi.getItem().getId(),
                            new Sales(oi.getItem().getId(), oi.getItem().getName(),
                                    oi.getPrice() * oi.getQuantity(),
                                    oi.getQuantity(), formattedDateOrder));
                }
            }
        }

        sortMonths(maps, allMonths);
        return maps.values().stream().sorted(Comparator.comparingLong(Sales::getItemId)).toList();
    }

    private void sortMonths(Map<Long, Sales> maps, List<String> allMonths) {
        for (Sales s : maps.values()) {
            for (String month : allMonths)
                if (!s.getSalesPerMonth().containsKey(month))
                    s.getSalesPerMonth().put(month, new PriceItemDTO(0, 0));

            Collections.sort(new ArrayList<String>(s.getSalesPerMonth().keySet()));
        }

    }

    @Override
    public IncomeExpenses getIncomeExpenses(long dateFrom, long dateTo) {
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

            sum += calculateExpensesPerEmployee(dateFrom, dateTo, e);

        }

        return Math.round(sum * 100.0) / 100.0;
    }

    public double calculateExpensesPerEmployee(long dateFrom, long dateTo, Employee e) {
        LocalDate dateFromLD = Instant.ofEpochMilli(dateFrom).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate dateToLD = Instant.ofEpochMilli(dateTo).atZone(ZoneId.systemDefault()).toLocalDate();
        double sum = 0;
        // ide od najvecih ka manjim datumima je sortirano
        List<Salary> l = e.getSalaries().stream().sorted(Comparator.comparingLong(Salary::getDateFrom).reversed()).toList();

        while (!dateFromLD.isAfter(dateToLD)) {
            for (Salary s : l) {
                boolean indicator = false;
                LocalDate dateOfSalary = Instant.ofEpochMilli(s.getDateFrom()).atZone(ZoneId.systemDefault()).toLocalDate();
                if (dateOfSalary.equals(dateToLD) || dateOfSalary.isBefore(dateToLD)) {
                    sum += s.getAmount() / dateToLD.getMonth().minLength();
                    indicator = true;
                }
                if (indicator)
                    break;
            }
            dateToLD = dateToLD.minusDays(1);
        }
        return Math.round(sum * 100.0) / 100.0;
    }
    
    @Override
    public List<UserReportDTO> activityReport(long dateFrom, long dateTo) {
        List<Order> orders = orderService.getOrdersByDate(dateFrom, dateTo);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.yyyy.");
        List<String> allMonths = new ArrayList<>();

        Map<Long, UserReportDTO> occurrenceMap = new HashMap<>();
        orders.forEach((order -> {
            LocalDate dateOrder = Instant.ofEpochMilli(order.getCreatedAt()).atZone(ZoneId.systemDefault()).toLocalDate();
            String formattedDateOrder = dateOrder.format(formatter);
            if (!allMonths.contains(formattedDateOrder))
                allMonths.add(formattedDateOrder);

            Long barmenId = order.getBarman() != null ? order.getBarman().getId() : -1;
            Long cookId = order.getCook() != null ? order.getCook().getId() : -1;
            Long waiterId = order.getWaiter().getId();
            if (barmenId != -1)
                putEmployeeIntoMap(occurrenceMap, barmenId, order.getBarman().getFirstName(),
                        order.getBarman().getLastName(), UserType.BARMAN, formattedDateOrder);
            if (cookId != -1)
                putEmployeeIntoMap(occurrenceMap, cookId, order.getCook().getFirstName(),
                        order.getCook().getLastName(), UserType.COOK, formattedDateOrder);

            putEmployeeIntoMap(occurrenceMap, waiterId, order.getWaiter().getFirstName(),
                    order.getWaiter().getLastName(), UserType.WAITER, formattedDateOrder);
        }));
        List<UserReportDTO> userReportDTOS = new ArrayList<>();
        occurrenceMap.forEach((key, value) -> userReportDTOS.add(value));
        sortMonthsOfEmployee(userReportDTOS, allMonths);
        return userReportDTOS;
    }

    private void putEmployeeIntoMap(Map<Long, UserReportDTO> occurrenceMap, Long employeeId, String firstName,
                                    String lastName, UserType userType, String formattedDateOrder) {
        if (!occurrenceMap.containsKey(employeeId)) {
            occurrenceMap.put(employeeId, new UserReportDTO(employeeId, firstName,
                    lastName, userType, 1L, formattedDateOrder));
        } else {
            occurrenceMap.get(employeeId).setOrdersAccomplished(occurrenceMap.get(employeeId)
                        .getOrdersAccomplished() + 1);
            if (occurrenceMap.get(employeeId).getOrdersPerMonth().containsKey(formattedDateOrder)) {
                occurrenceMap.get(employeeId).getOrdersPerMonth()
                        .put(formattedDateOrder,
                                occurrenceMap.get(employeeId).getOrdersPerMonth().get(formattedDateOrder) + 1);
            } else {
                occurrenceMap.get(employeeId).getOrdersPerMonth().put(formattedDateOrder, 1);
            }
        }
    }

    private void sortMonthsOfEmployee(List<UserReportDTO> userReportDTOS, List<String> allMonths) {
        for (UserReportDTO userReportDTO : userReportDTOS) {
            for (String month : allMonths)
                if (!userReportDTO.getOrdersPerMonth().containsKey(month))
                    userReportDTO.getOrdersPerMonth().put(month, 0);

            Collections.sort(new ArrayList<String>(userReportDTO.getOrdersPerMonth().keySet()));
        }
    }
}
