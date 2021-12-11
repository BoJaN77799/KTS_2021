package com.app.RestaurantApp.reports;

import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.order.OrderService;
import com.app.RestaurantApp.users.employee.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static com.app.RestaurantApp.reports.Constants.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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

    }

    @Test
    public void testGetReportsSales() {

    }

    @Test
    public void testGetIncomeExpenses() {

    }

    @Test
    public void testCalculateIncome() {

    }

    @Test
    public void testCalculateExpenses() {

    }

    @Test
    public void testCalculateExpensesPerEmployee(){

    }

    private List<Order> createOrders() {
        List<Order> orders = new ArrayList<>();
        return orders;
    }
}
