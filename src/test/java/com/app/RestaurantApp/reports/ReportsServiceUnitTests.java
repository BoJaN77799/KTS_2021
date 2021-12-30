package com.app.RestaurantApp.reports;

import com.app.RestaurantApp.enums.OrderStatus;
import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.order.OrderService;
import com.app.RestaurantApp.reports.dto.UserReportDTO;
import com.app.RestaurantApp.users.employee.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.app.RestaurantApp.reports.Constants.*;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;

import com.app.RestaurantApp.bonus.Bonus;
import com.app.RestaurantApp.item.Item;
import com.app.RestaurantApp.orderItem.OrderItem;
import com.app.RestaurantApp.reports.dto.IncomeExpenses;
import com.app.RestaurantApp.reports.dto.Sales;
import com.app.RestaurantApp.salary.Salary;
import com.app.RestaurantApp.users.employee.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ReportsServiceUnitTests {

    @Autowired
    private ReportsService reportsService;

    @MockBean
    private OrderService orderServiceMock;

    @MockBean
    private EmployeeService employeeServiceMock;

    @BeforeEach
    public void setup() {
        List<Order> orders = createOrders();
        given(orderServiceMock.findAllOrderInIntervalOfDates(YESTERDAY, CURRENT_DATE)).willReturn(orders);

        List<Employee> employees = createEmployees();
        given(employeeServiceMock.findAllEmployeesWithSalariesAndBonuses(false)).willReturn(employees);
    }

    @Test
    public void testActivityReport() {
        // Preparing data
        Employee waiter = new Employee(3L);
        Employee cook = new Employee(4L);
        Employee barman = new Employee(5L);
        List<Order> mockedOrders = new ArrayList<>(
                Arrays.asList(
                        new Order(1L, OrderStatus.FINISHED, 1637770076405L, waiter, barman, null),
                        new Order(2L, OrderStatus.FINISHED, 1637770076405L, waiter, barman, null),
                        new Order(3L, OrderStatus.FINISHED, 1637770076405L, waiter, null, cook),
                        new Order(4L, OrderStatus.FINISHED, 1637770076405L, waiter, null, null),
                        new Order(5L, OrderStatus.FINISHED, 1637770076405L, waiter, barman, cook))
        );
        // Mocking
        given(orderServiceMock.getOrdersByDate(DATE_FROM, DATE_TO)).willReturn(mockedOrders);

        // Test invoke
        List<UserReportDTO> activityReport = reportsService.activityReport(DATE_FROM, DATE_TO);

        // Verifying
        assertNotNull(activityReport);
        assertEquals(3, activityReport.size());
        assertEquals(5, activityReport.stream().filter(user -> user.getUserType() == UserType.WAITER).findAny().get().getOrdersAccomplished());
        assertEquals(2, activityReport.stream().filter(user -> user.getUserType() == UserType.COOK).findAny().get().getOrdersAccomplished());
        assertEquals(3, activityReport.stream().filter(user -> user.getUserType() == UserType.BARMAN).findAny().get().getOrdersAccomplished());
    }

    @Test
    public void testGetReportsSales() {
        List<Sales> sales = reportsService.getReportsSales(YESTERDAY, CURRENT_DATE);

        assertEquals(3, sales.size());

        assertEquals(FIRST_ID, sales.get(0).getItemId());
        assertEquals(FIRST_NAME, sales.get(0).getName());
        assertEquals(7, sales.get(0).getItemCount());
        assertEquals(600.0, sales.get(0).getPriceCount());

        assertEquals(SECOND_ID, sales.get(1).getItemId());
        assertEquals(SECOND_NAME, sales.get(1).getName());
        assertEquals(8, sales.get(1).getItemCount());
        assertEquals(1350.0, sales.get(1).getPriceCount());

        assertEquals(THIRD_ID, sales.get(2).getItemId());
        assertEquals(THIRD_NAME, sales.get(2).getName());
        assertEquals(2, sales.get(2).getItemCount());
        assertEquals(800.0, sales.get(2).getPriceCount());
    }

    @Test
    public void testGetIncomeExpenses() {
        List<Order> orders = createOrders();
        given(orderServiceMock.findAllOrderInIntervalOfDates(LAST_MONTH, CURRENT_DATE)).willReturn(orders);

        IncomeExpenses ie = reportsService.getIncomeExpenses(LAST_MONTH, CURRENT_DATE);

        assertEquals(600.0, ie.getIncome());
        assertEquals(66817.20, ie.getExpenses());
    }

    @Test
    public void testCalculateIncome() {
        List<Order> orders = createOrders();

        assertEquals(600.0, reportsService.calculateIncome(orders));

        Order o = new Order();
        o.setProfit(50.0);
        orders.add(o);

        assertEquals(650.0, reportsService.calculateIncome(orders));
    }

    @Test
    public void testCalculateExpenses() {
        List<Employee> employees = createEmployees();

        Employee e1 = new Employee();
        e1.setSalaries(new HashSet<>());
        e1.setBonuses(new HashSet<>());

        Salary s1 = new Salary(100000, LAST_MIDDLE_MONTH, e1);
        e1.getSalaries().add(s1);

        Bonus b1 = new Bonus(5000, LAST_MIDDLE_MONTH, e1);
        e1.getBonuses().add(b1);

        employees.add(e1);

        given(employeeServiceMock.findAllEmployeesWithSalariesAndBonuses(false)).willReturn(employees);

        assertEquals(120634.4, reportsService.calculateExpenses(LAST_MONTH, CURRENT_DATE));
    }

    @Test
    public void testCalculateExpensesPerEmployee(){
        Employee e1 = new Employee();
        e1.setSalaries(new HashSet<>());
        e1.setBonuses(new HashSet<>());

        Salary s1 = new Salary(10000, LAST_MIDDLE_MONTH, e1);
        e1.getSalaries().add(s1);

        Salary s2 = new Salary(12000, LAST_WEEK, e1);
        e1.getSalaries().add(s2);

        double sum = reportsService.calculateExpensesPerEmployee(LAST_MONTH, CURRENT_DATE, e1);
        assertEquals(5397.85, sum);

        Employee e2 = new Employee();
        e2.setSalaries(new HashSet<>());
        e2.setBonuses(new HashSet<>());

        Salary s3 = new Salary(20000, LAST_MONTH, e2);
        e2.getSalaries().add(s3);

        Salary s4 = new Salary(30000, LAST_WEEK, e2);
        e2.getSalaries().add(s4);

        sum = reportsService.calculateExpensesPerEmployee(LAST_MONTH, CURRENT_DATE, e2);
        assertEquals(23010.75, sum);

        Employee e3 = new Employee();
        e3.setSalaries(new HashSet<>());
        e3.setBonuses(new HashSet<>());

        Salary s5 = new Salary(50000, LAST_MIDDLE_MONTH, e3);
        e3.getSalaries().add(s5);

        sum = reportsService.calculateExpensesPerEmployee(LAST_MONTH, CURRENT_DATE, e3);
        assertEquals(24408.60 , sum);

    }

    private List<Order> createOrders() {
        List<Order> orders = new ArrayList<>();

        Item i1 = new Item(FIRST_ID, FIRST_NAME, 100.0, 50.0);

        Item i2 = new Item(SECOND_ID, SECOND_NAME, 200.0, 100.0);

        Item i3 = new Item(THIRD_ID, THIRD_NAME, 400.0, 200.0);

        // FIRST ORDER
        Order o1 = new Order();
        o1.setId(FIRST_ID);
        o1.setOrderItems(new HashSet<>());
        o1.setProfit(200.0);

        OrderItem oi1 = new OrderItem(FIRST_ID, o1, i1, 100.0, 5);
        o1.getOrderItems().add(oi1);

        OrderItem oi2 = new OrderItem(SECOND_ID, o1, i2, 200.0, 3);
        o1.getOrderItems().add(oi2);

        OrderItem oi3 = new OrderItem(THIRD_ID, o1, i3, 400.0, 1);
        o1.getOrderItems().add(oi3);

        orders.add(o1);

        // SECOND ORDER
        Order o2 = new Order();
        o2.setId(SECOND_ID);
        o2.setOrderItems(new HashSet<>());
        o2.setProfit(400.0);

        OrderItem oi4 = new OrderItem(FOURTH_ID, o2, i1, 50, 2);
        o2.getOrderItems().add(oi4);

        OrderItem oi5 = new OrderItem(FIFTH_ID, o2, i2, 150.0, 5);
        o2.getOrderItems().add(oi5);

        orders.add(o2);

        // THIRD ORDER
        Order o3 = new Order();
        o3.setId(THIRD_ID);
        o3.setOrderItems(new HashSet<>());

        OrderItem oi6 = new OrderItem(SIXTH_ID, o3, i3, 400.0, 1);
        o3.getOrderItems().add(oi6);

        orders.add(o3);

        return orders;
    }

    private List<Employee> createEmployees() {
        List<Employee> employees = new ArrayList<>();

        Employee e1 = new Employee();
        e1.setSalaries(new HashSet<>());
        e1.setBonuses(new HashSet<>());

        Salary s1 = new Salary(10000, LAST_MIDDLE_MONTH, e1);
        e1.getSalaries().add(s1);

        Salary s2 = new Salary(12000, LAST_WEEK, e1);
        e1.getSalaries().add(s2);

        Bonus b1 = new Bonus(1500, LAST_MIDDLE_MONTH, e1);
        e1.getBonuses().add(b1);

        employees.add(e1);

        Employee e2 = new Employee();
        e2.setSalaries(new HashSet<>());
        e2.setBonuses(new HashSet<>());

        Salary s3 = new Salary(20000, LAST_MONTH, e2);
        e2.getSalaries().add(s3);

        Salary s4 = new Salary(30000, LAST_WEEK, e2);
        e2.getSalaries().add(s4);

        Bonus b2 = new Bonus(1000, YESTERDAY, e2);
        e2.getBonuses().add(b2);

        Bonus b3 = new Bonus(1500, LAST_MIDDLE_MONTH, e2);
        e2.getBonuses().add(b3);

        employees.add(e2);

        Employee e3 = new Employee();
        e3.setSalaries(new HashSet<>());
        e3.setBonuses(new HashSet<>());

        Salary s5 = new Salary(50000, LAST_MIDDLE_MONTH, e3);
        e3.getSalaries().add(s5);

        Bonus b4 = new Bonus(10000, LAST_WEEK, e3);
        e3.getBonuses().add(b4);

        employees.add(e3);

        return employees;
    }
}
