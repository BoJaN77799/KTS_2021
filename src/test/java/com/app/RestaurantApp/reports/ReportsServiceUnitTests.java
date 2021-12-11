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

@SpringBootTest
public class ReportsServiceUnitTests {

    @Autowired
    private ReportsService reportsService;

    @MockBean
    private OrderService orderServiceMock;

    @Test
    public void testActivityReport(){
        // Preparing data
        Employee waiter = new Employee(3L);
        Employee cook = new Employee(4L);
        Employee barman = new Employee(5L);
        List<Order> mockedOrders = new ArrayList<>(
                Arrays.asList(
                        new Order(OrderStatus.FINISHED, 1637770076405L, waiter, barman, null),
                        new Order(OrderStatus.FINISHED, 1637770076405L, waiter, barman, null),
                        new Order(OrderStatus.FINISHED, 1637770076405L, waiter, null, cook),
                        new Order(OrderStatus.FINISHED, 1637770076405L, waiter, null, null),
                        new Order(OrderStatus.FINISHED, 1637770076405L, waiter, barman, cook))
                );
        // Mocking
        given(orderServiceMock.getOrdersByDate(DATE_FROM, DATE_TO)).willReturn(mockedOrders);

        // Test invoke
        List<UserReportDTO> activityReport = reportsService.activityReport(DATE_FROM, DATE_TO);

        // Verifying
        assertNotNull(activityReport);
        assertEquals(3, activityReport.size());
        assertEquals( 5, activityReport.stream().filter(user -> user.getUserType() == UserType.WAITER).findAny().get().getOrdersAccomplished());
        assertEquals( 2, activityReport.stream().filter(user -> user.getUserType() == UserType.COOK).findAny().get().getOrdersAccomplished());
        assertEquals( 3, activityReport.stream().filter(user -> user.getUserType() == UserType.BARMAN).findAny().get().getOrdersAccomplished());
    }
}
