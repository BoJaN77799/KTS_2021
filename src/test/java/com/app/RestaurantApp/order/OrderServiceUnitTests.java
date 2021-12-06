package com.app.RestaurantApp.order;

import com.app.RestaurantApp.drinks.Drink;
import com.app.RestaurantApp.enums.OrderStatus;
import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.food.Food;
import com.app.RestaurantApp.item.Item;
import com.app.RestaurantApp.item.ItemRepository;
import com.app.RestaurantApp.item.ItemService;
import com.app.RestaurantApp.notifications.OrderNotification;
import com.app.RestaurantApp.notifications.OrderNotificationService;
import com.app.RestaurantApp.order.dto.OrderDTO;
import com.app.RestaurantApp.orderItem.dto.OrderItemOrderCreationDTO;
import com.app.RestaurantApp.table.Table;
import com.app.RestaurantApp.table.TableService;
import com.app.RestaurantApp.users.employee.Employee;
import com.app.RestaurantApp.users.employee.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

import static com.app.RestaurantApp.order.Constants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class OrderServiceUnitTests {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepositoryMock;

    @MockBean
    private EmployeeService employeeServiceMock;

    @MockBean
    private TableService tableServiceMock;

    @MockBean
    private OrderNotificationService orderNotificationServiceMock;

    @MockBean
    private ItemService itemServiceMock;

    @BeforeEach
    public void setup() {
        Employee waiter = new Employee();
        waiter.setId(1L);
        waiter.setUserType(UserType.WAITER);

        Table table = new Table();
        table.setId(1L);

        Order order = new Order();
        order.setId(1L);

        OrderNotification orderNotification1 = new OrderNotification();
        OrderNotification orderNotification2 = new OrderNotification();
        List<OrderNotification> orderNotifications = new ArrayList<>();
        orderNotifications.add(orderNotification1);
        orderNotifications.add(orderNotification2);

        given(employeeServiceMock.findById(1L)).willReturn(waiter);
        given(employeeServiceMock.findById(null)).willReturn(null);

        given(tableServiceMock.findById(1L)).willReturn(table);
        given(tableServiceMock.findById(null)).willReturn(null);

        given(orderRepositoryMock.save(any(Order.class))).willReturn(order);
        given(orderNotificationServiceMock.notifyNewOrder(any(Order.class))).willReturn(orderNotifications);
        given(itemServiceMock.findAllWithIds(anyList())).willReturn(createItems());
    }

    @Test
    public void testCreateOrder() throws OrderException {
        OrderDTO orderDTO = createOrderDTO();

        Order order = orderService.createOrder(orderDTO);

        verify(employeeServiceMock, times(1)).findById(1L);
        verify(tableServiceMock, times(1)).findById(1L);
        verify(orderRepositoryMock, times(1)).save(any(Order.class));
        verify(orderNotificationServiceMock, times(1)).notifyNewOrder(order);
        assertEquals(Long.valueOf(1), order.getId());
        assertEquals(Long.valueOf(1), order.getWaiter().getId());
        assertEquals(Long.valueOf(1), order.getTable().getId());
        assertEquals(OrderStatus.NEW, order.getStatus());
        assertEquals(NOTE, order.getNote());
        assertEquals(2, order.getOrderItems().size());
    }

    private OrderDTO createOrderDTO() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(null);
        orderDTO.setWaiterId(1L);
        orderDTO.setTableId(1L);

        OrderItemOrderCreationDTO oi1DTO = new OrderItemOrderCreationDTO(null, 1L, INIT_QUANTITY1, PRICE1, 1);
        OrderItemOrderCreationDTO oi2DTO = new OrderItemOrderCreationDTO(null, 2L, INIT_QUANTITY1, PRICE2, 2);
        List<OrderItemOrderCreationDTO> ois = new ArrayList<>();
        ois.add(oi1DTO);
        ois.add(oi2DTO);

        orderDTO.setOrderItems(ois);
        orderDTO.setCreatedAt(null);
        orderDTO.setStatus(null);
        orderDTO.setNote(NOTE);

        return orderDTO;
    }

    private List<Item> createItems() {
        Food f = new Food();
        f.setId(1L);
        f.setName(FOOD_NAME);
        f.setCurrentPrice(PRICE1);

        Drink d = new Drink();
        d.setId(2L);
        d.setName(DRINK_NAME);
        d.setCurrentPrice(PRICE1);

        List<Item> items = new ArrayList<>();
        items.add(f);
        items.add(d);
        return items;
    }

}
