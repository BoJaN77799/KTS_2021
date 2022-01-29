package com.app.RestaurantApp.notifications;

import com.app.RestaurantApp.drinks.Drink;
import com.app.RestaurantApp.enums.OrderItemStatus;
import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.food.Food;
import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.order.OrderException;
import com.app.RestaurantApp.orderItem.OrderItem;
import com.app.RestaurantApp.table.Table;
import com.app.RestaurantApp.users.employee.Employee;
import com.app.RestaurantApp.users.employee.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static com.app.RestaurantApp.notifications.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith({SpringExtension.class})
@SpringBootTest
public class OrderNotificationServiceUnitTests {

    @Autowired
    private OrderNotificationService orderNotificationService;

    @MockBean
    private OrderNotificationRepository orderNotificationRepository;

    @MockBean
    private EmployeeService employeeService;

    @BeforeEach
    public void setup() {
        List<Employee> employees = new ArrayList<>();
        employees.add(createEmployee(1L, UserType.WAITER));
        employees.add(createEmployee(2L, UserType.COOK));
        employees.add(createEmployee(3L, UserType.COOK));
        employees.add(createEmployee(4L, UserType.BARMAN));

        given(employeeService.findAll()).willReturn(employees);
    }

    @Test
    public void testNotifyNewOrder_WithFood() {
        Order order = createOrderWithFoods();
        List<OrderNotification> orderNotifications = orderNotificationService.notifyNewOrder(order);

        verify(employeeService, times(1)).findAll();
        assertEquals(Long.valueOf(1), orderNotifications.get(0).getOrder().getId());
        assertEquals(2, orderNotifications.size());
        assertEquals(UserType.COOK, orderNotifications.get(0).getEmployee().getUserType());
        assertEquals(UserType.COOK, orderNotifications.get(1).getEmployee().getUserType());
        assertEquals(NEW_ORDER_MESSAGE, orderNotifications.get(0).getMessage());
    }

    @Test
    public void testNotifyNewOrder_WithDrinks() {
        Order order = createOrderWithDrinks();
        List<OrderNotification> orderNotifications = orderNotificationService.notifyNewOrder(order);

        verify(employeeService, times(1)).findAll();
        assertEquals(Long.valueOf(1), orderNotifications.get(0).getOrder().getId());
        assertEquals(1, orderNotifications.size());
        assertEquals(UserType.BARMAN, orderNotifications.get(0).getEmployee().getUserType());
        assertEquals(NEW_ORDER_MESSAGE, orderNotifications.get(0).getMessage());
    }

    @Test
    public void testNotifyNewOrder_WithFoodAndDrink() {
        Order order = createOrderWithFoodAndDrink();
        List<OrderNotification> orderNotifications = orderNotificationService.notifyNewOrder(order);

        verify(employeeService, times(1)).findAll();
        assertEquals(Long.valueOf(1), orderNotifications.get(0).getOrder().getId());
        assertEquals(3, orderNotifications.size());
        assertEquals(NEW_ORDER_MESSAGE, orderNotifications.get(0).getMessage());
    }

    @Test
    public void testNotifyOrderItemChange_FoodAndCook() {
        Order order = createBlankOrder();

        order.setCook(createEmployee(1L, UserType.COOK));
        OrderItem oldOrderItem = createFoodOrderItem();

        OrderNotification orderNotification = orderNotificationService.notifyOrderItemChange(order, oldOrderItem, NEW_QUANTITY, NEW_PRIORITY);
        assertEquals(Long.valueOf(1), orderNotification.getOrder().getId());
        assertEquals(Long.valueOf(1), orderNotification.getEmployee().getId());
        assertEquals(ORDER_ITEM_CHANGE_MESSAGE, orderNotification.getMessage());
        assertNotEquals(UserType.BARMAN, orderNotification.getEmployee().getUserType());
        assertNotEquals(UserType.WAITER, orderNotification.getEmployee().getUserType());
    }

    @Test
    public void testNotifyOrderItemChange_FoodAndBarman() {
        Order order = createBlankOrder();

        order.setBarman(createEmployee(1L, UserType.BARMAN));
        OrderItem oldOrderItem = createFoodOrderItem();

        OrderNotification orderNotification = orderNotificationService.notifyOrderItemChange(order, oldOrderItem, NEW_QUANTITY, NEW_PRIORITY);
        assertNull(orderNotification);
    }

    @Test
    public void testNotifyOrderItemChange_DrinkAndCook() {
        Order order = createBlankOrder();

        order.setCook(createEmployee(1L, UserType.COOK));
        OrderItem oldOrderItem = createDrinkOrderItem();

        OrderNotification orderNotification = orderNotificationService.notifyOrderItemChange(order, oldOrderItem, NEW_QUANTITY, NEW_PRIORITY);
        assertNull(orderNotification);
    }

    @Test
    public void testNotifyOrderItemChange_NoChange() {
        Order order = createBlankOrder();

        order.setCook(createEmployee(1L, UserType.COOK));
        OrderItem oldOrderItem = createFoodOrderItem();

        OrderNotification orderNotification = orderNotificationService.notifyOrderItemChange(order, oldOrderItem, OLD_QUANTITY, OLD_PRIORITY);
        assertNull(orderNotification);
    }

    @Test
    public void testNotifyOrderItemChange_PriorityToDef() {
        Order order = createBlankOrder();

        order.setCook(createEmployee(1L, UserType.COOK));
        OrderItem oldOrderItem = createFoodOrderItem();

        OrderNotification orderNotification = orderNotificationService.notifyOrderItemChange(order, oldOrderItem, OLD_QUANTITY, NEW_PRIORITY_DEF);
        assertEquals(Long.valueOf(1), orderNotification.getOrder().getId());
        assertEquals(Long.valueOf(1), orderNotification.getEmployee().getId());
        assertEquals(PRIORITY_CHANGE_DEF_MSG, orderNotification.getMessage());
        assertNotEquals(UserType.BARMAN, orderNotification.getEmployee().getUserType());
        assertNotEquals(UserType.WAITER, orderNotification.getEmployee().getUserType());
    }

    @Test
    public void testNotifyOrderItemAdded() {
        Order order = createBlankOrder();
        order.setCook(createEmployee(1L, UserType.COOK));
        order.setBarman(createEmployee(2L, UserType.BARMAN));

        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(createFoodOrderItem());
        orderItems.add(createDrinkOrderItem());

        List<OrderNotification> orderNotifications = orderNotificationService.notifyOrderItemAdded(order, orderItems);
        orderNotifications.sort((n1, n2) -> (int) ( n1.getEmployee().getId() - n2.getEmployee().getId()));
        assertEquals(2, orderNotifications.size());
        assertEquals(Long.valueOf(1), orderNotifications.get(0).getOrder().getId());
        assertEquals(Long.valueOf(2), orderNotifications.get(1).getEmployee().getId());
        assertEquals(UserType.BARMAN, orderNotifications.get(1).getEmployee().getUserType());
    }

    @Test
    public void testNotifyOrderItemAdded_OnlyBarman() {
        Order order = createBlankOrder();
        order.setBarman(createEmployee(2L, UserType.BARMAN));

        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(createFoodOrderItem());
        orderItems.add(createDrinkOrderItem());

        List<OrderNotification> orderNotifications = orderNotificationService.notifyOrderItemAdded(order, orderItems);
        assertEquals(1, orderNotifications.size());
        assertEquals(Long.valueOf(1), orderNotifications.get(0).getOrder().getId());
        assertEquals(Long.valueOf(2), orderNotifications.get(0).getEmployee().getId());
        assertEquals(UserType.BARMAN, orderNotifications.get(0).getEmployee().getUserType());
    }

    @Test
    public void testNotifyOrderItemAdded_CookAndDrink() {
        Order order = createBlankOrder();
        order.setCook(createEmployee(1L, UserType.COOK));

        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(createDrinkOrderItem());

        List<OrderNotification> orderNotifications = orderNotificationService.notifyOrderItemAdded(order, orderItems);
        assertEquals(0, orderNotifications.size());
    }

    @Test
    public void testNotifyOrderItemAdded_NoEmployees() {
        Order order = createBlankOrder();

        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(createFoodOrderItem());
        orderItems.add(createDrinkOrderItem());

        List<OrderNotification> orderNotifications = orderNotificationService.notifyOrderItemAdded(order, orderItems);
        assertEquals(0, orderNotifications.size());
    }

    @Test
    public void testNotifyOrderItemDeleted() {
        Order order = createBlankOrder();
        order.setCook(createEmployee(1L, UserType.COOK));
        OrderItem oi = createFoodOrderItemWithName();

        OrderNotification orderNotification = orderNotificationService.notifyOrderItemDeleted(order, oi);
        assertEquals(1, orderNotification.getOrder().getId());
        assertEquals(UserType.COOK, orderNotification.getEmployee().getUserType());
        assertEquals(ORDER_ITEM_DELETED_MSG, orderNotification.getMessage());
    }

    @Test
    public void testNotifyOrderItemDeleted_BarmanAndFood() {
        Order order = createBlankOrder();
        order.setBarman(createEmployee(1L, UserType.BARMAN));
        OrderItem oi = createFoodOrderItemWithName();

        OrderNotification orderNotification = orderNotificationService.notifyOrderItemDeleted(order, oi);
        assertNull(orderNotification);
    }

    @Test
    public void testNotifyOrderItemDeleted_CookAndDrink() {
        Order order = createBlankOrder();
        order.setCook(createEmployee(1L, UserType.COOK));
        OrderItem oi = createDrinkOrderItemWithName();

        OrderNotification orderNotification = orderNotificationService.notifyOrderItemDeleted(order, oi);
        assertNull(orderNotification);
    }

    @Test
    public void testNotifyOrderItemDeleted_NoEmployees() {
        Order order = createBlankOrder();
        order.setCook(createEmployee(1L, UserType.COOK));
        OrderItem oi = createDrinkOrderItemWithName();

        OrderNotification orderNotification = orderNotificationService.notifyOrderItemDeleted(order, oi);
        assertNull(orderNotification);
    }

    @Test
    public void testNotifyWaiterOrderItemStatusFinished_Food() {
        Order order = createBlankOrder();
        order.setWaiter(createEmployee(1L, UserType.WAITER));
        OrderItem oi = createFoodOrderItem();
        oi.setOrder(order);

        OrderNotification orderNotification = orderNotificationService.notifyWaiterOrderItemStatusFinished(oi);
        assertEquals(Long.valueOf(1), orderNotification.getEmployee().getId());
        assertEquals(ORDER_ITEM_FOOD_FINISHED_MSG, orderNotification.getMessage());
    }

    @Test
    public void testNotifyWaiterOrderItemStatusFinished_Drink() {
        Order order = createBlankOrder();
        order.setWaiter(createEmployee(1L, UserType.WAITER));
        OrderItem oi = createDrinkOrderItem();
        oi.setOrder(order);

        OrderNotification orderNotification = orderNotificationService.notifyWaiterOrderItemStatusFinished(oi);
        assertEquals(Long.valueOf(1), orderNotification.getEmployee().getId());
        assertEquals(ORDER_ITEM_DRINK_FINISHED_MSG, orderNotification.getMessage());
    }

    @Test
    public void testSetSeenAllByEmployee_InvalidEmployeeId() {
        given(employeeService.findById(3L)).willReturn(null);

        Exception e = assertThrows(OrderNotificationException.class, () -> orderNotificationService.setSeenAllByEmployee(3L));

        assertEquals(INVALID_EMPLOYEE_MSG, e.getMessage());
    }

    @Test
    public void testSetSeenAllByEmployee() throws OrderNotificationException {
        Employee employee = new Employee();
        employee.setId(3L);

        given(employeeService.findById(3L)).willReturn(employee);
        orderNotificationService.setSeenAllByEmployee(3L);
        verify(orderNotificationRepository, times(1)).setSeenAllByEmployee(3L);
    }

    @Test
    public void testSetSeen_InvalidId() throws OrderNotificationException {
        given(orderNotificationRepository.findById(-1L)).willReturn(Optional.empty());

        Exception e = assertThrows(OrderNotificationException.class, () -> orderNotificationService.setSeen(-1L));

        assertEquals(INVALID_ORDER_NOTIF_MSG, e.getMessage());
    }

    @Test
    public void testSetSeen() throws OrderNotificationException {
        OrderNotification orderNotification = new OrderNotification();
        given(orderNotificationRepository.findById(1L)).willReturn(Optional.of(orderNotification));

        orderNotificationService.setSeen(1L);

        assertTrue(orderNotification.isSeen());
        verify(orderNotificationRepository, times(1)).save(orderNotification);
    }

    private Order createBlankOrder() {
        Order order = new Order();
        order.setId(1L);

        Table table = new Table();
        table.setId(1L);
        order.setTable(table);

        order.setOrderItems(new HashSet<>());

        return order;
    }

    private Order createOrderWithFoods() {
        Order order = new Order();
        order.setId(1L);

        Table table = new Table();
        table.setId(1L);
        order.setTable(table);

        Food f1 = new Food();
        Food f2 = new Food();
        OrderItem oi1 = new OrderItem();
        OrderItem oi2 = new OrderItem();
        oi1.setItem(f1);
        oi2.setItem(f2);

        order.setOrderItems(new HashSet<>());
        order.getOrderItems().add(oi1);
        order.getOrderItems().add(oi2);

        return order;
    }

    private Order createOrderWithDrinks() {
        Order order = new Order();
        order.setId(1L);

        Table table = new Table();
        table.setId(1L);
        order.setTable(table);

        Drink d1 = new Drink();
        Drink d2 = new Drink();
        OrderItem oi1 = new OrderItem();
        OrderItem oi2 = new OrderItem();
        oi1.setItem(d1);
        oi2.setItem(d2);

        order.setOrderItems(new HashSet<>());
        order.getOrderItems().add(oi1);
        order.getOrderItems().add(oi2);

        return order;
    }

    private Order createOrderWithFoodAndDrink() {
        Order order = new Order();
        order.setId(1L);

        Table table = new Table();
        table.setId(1L);
        order.setTable(table);

        Food f = new Food();
        Drink d = new Drink();
        OrderItem oi1 = new OrderItem();
        OrderItem oi2 = new OrderItem();
        oi1.setItem(f);
        oi2.setItem(d);

        order.setOrderItems(new HashSet<>());
        order.getOrderItems().add(oi1);
        order.getOrderItems().add(oi2);

        return order;
    }

    private Employee createEmployee(Long id, UserType type) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setUserType(type);
        return employee;
    }

    private OrderItem createFoodOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setPriority(OLD_PRIORITY);
        orderItem.setQuantity(OLD_QUANTITY);
        Food f = new Food();
        f.setName(FOOD_NAME);
        orderItem.setItem(f);

        return orderItem;
    }

    private OrderItem createDrinkOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setPriority(OLD_PRIORITY);
        orderItem.setQuantity(OLD_QUANTITY);
        Drink d = new Drink();
        d.setName(DRINK_NAME);
        orderItem.setItem(d);

        return orderItem;
    }

    private OrderItem createFoodOrderItemWithName() {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(new Food());
        orderItem.getItem().setName(FOOD_NAME);

        return orderItem;
    }

    private OrderItem createDrinkOrderItemWithName() {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(new Drink());
        orderItem.getItem().setName(DRINK_NAME);

        return orderItem;
    }
}
