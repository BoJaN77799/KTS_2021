package com.app.RestaurantApp.reports;

import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.order.OrderService;
import com.app.RestaurantApp.reports.dto.IncomeExpenses;
import com.app.RestaurantApp.reports.dto.Sales;
import com.app.RestaurantApp.reports.dto.UserReportDTO;
import com.app.RestaurantApp.users.employee.Employee;
import com.app.RestaurantApp.users.employee.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.app.RestaurantApp.reports.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportsServiceIntegrationTests {

    @Autowired
    private ReportsService reportsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void testActivityReport() {
        // Test invoke
        List<UserReportDTO> activityReport = reportsService.activityReport(DATE_FROM, DATE_TO);

        // Verifying
        assertNotNull(activityReport);
        assertEquals(3, activityReport.size());
        assertEquals( 6, activityReport.stream().filter(user -> user.getUserType() == UserType.WAITER).findAny().get().getOrdersAccomplished());
        assertEquals( 3, activityReport.stream().filter(user -> user.getUserType() == UserType.COOK).findAny().get().getOrdersAccomplished());
        assertEquals( 3, activityReport.stream().filter(user -> user.getUserType() == UserType.BARMAN).findAny().get().getOrdersAccomplished());
    }

    @Test
    public void testGetReportsSales() {
        List<Sales> sales = reportsService.getReportsSales(LAST_THREE_MONTHS, CURRENT_DATE);
        assertEquals(5, sales.size());

        assertEquals(1L, sales.get(0).getItemId());
        assertEquals(1620.0, sales.get(0).getPriceCount());
        assertEquals(7, sales.get(0).getItemCount());

        assertEquals(2L, sales.get(1).getItemId());
        assertEquals(2400.0, sales.get(1).getPriceCount());
        assertEquals(4, sales.get(1).getItemCount());

        assertEquals(4L, sales.get(2).getItemId());
        assertEquals(27500.0, sales.get(2).getPriceCount());
        assertEquals(11, sales.get(2).getItemCount());

        assertEquals(7L, sales.get(3).getItemId());
        assertEquals(2060.0, sales.get(3).getPriceCount());
        assertEquals(12, sales.get(3).getItemCount());

        assertEquals(8L, sales.get(4).getItemId());
        assertEquals(740.0, sales.get(4).getPriceCount());
        assertEquals(2, sales.get(4).getItemCount());

    }

    @Test
    public void testGetIncomeExpenses() {
        IncomeExpenses ie = reportsService.getIncomeExpenses(LAST_THREE_MONTHS, CURRENT_DATE);
        assertEquals(996.13, ie.getExpenses());
        assertEquals(400, ie.getIncome());

        ie = reportsService.getIncomeExpenses(LAST_WEEK, CURRENT_DATE);
        assertEquals(77.42, ie.getExpenses());
        assertEquals(0, ie.getIncome());
    }

    @Test
    public void testCalculateIncome() {
        List<Order> orders = orderService.findAllOrderInIntervalOfDates(LAST_THREE_MONTHS, CURRENT_DATE);
        assertEquals(6, orders.size());

        double sum = reportsService.calculateIncome(orders);
        assertEquals(400.0, sum);

        orders = orderService.findAllOrderInIntervalOfDates(LAST_WEEK, CURRENT_DATE);
        sum = reportsService.calculateIncome(orders);
        assertEquals(0, sum);
    }

    @Test
    public void testCalculateExpenses() {
        double sum = reportsService.calculateExpenses(LAST_THREE_MONTHS, CURRENT_DATE);
        assertEquals(996.13, sum);

        sum = reportsService.calculateExpenses(LAST_WEEK, CURRENT_DATE);
        assertEquals(77.42, sum);
    }

    @Test
    public void testCalculateExpensesPerEmployee() {
        Employee e = employeeService.findByEmail(VALID_EMAIL);
        assertEquals(496.13, reportsService.calculateExpensesPerEmployee(LAST_THREE_MONTHS, CURRENT_DATE, e));

        e = employeeService.findByEmail(INVALID_EMAIL);
        assertEquals(0, reportsService.calculateExpensesPerEmployee(LAST_THREE_MONTHS, CURRENT_DATE, e));
    }

}
