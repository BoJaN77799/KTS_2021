package com.app.RestaurantApp.order;

import com.app.RestaurantApp.drinks.Drink;
import com.app.RestaurantApp.enums.*;
import com.app.RestaurantApp.food.Food;
import com.app.RestaurantApp.food.FoodException;
import com.app.RestaurantApp.item.Item;
import com.app.RestaurantApp.item.ItemRepository;
import com.app.RestaurantApp.item.ItemService;
import com.app.RestaurantApp.notifications.OrderNotification;
import com.app.RestaurantApp.notifications.OrderNotificationService;
import com.app.RestaurantApp.order.dto.OrderDTO;
import com.app.RestaurantApp.orderItem.OrderItem;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.app.RestaurantApp.order.Constants.*;
import static org.mockito.ArgumentMatchers.*;
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
        List<Order> orders = new ArrayList<>();
        orders.add(order);

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

        given(orderRepositoryMock.findActiveOrderByTable(1L)).willReturn(new ArrayList<>());
    }

    @Test
    public void testCreateOrder() throws OrderException {
        given(itemServiceMock.findAllWithIds(anyList())).willReturn(createItems());

        OrderDTO orderDTO = createOrderDTOWithOrderItems();
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

    @Test
    public void testCreateOrder_NoOrderItems() throws OrderException {
        given(itemServiceMock.findAllWithIds(anyList())).willReturn(null);

        OrderDTO orderDTO = createBlankOrderDTO();

        Exception e = assertThrows(OrderException.class, () -> orderService.createOrder(orderDTO));

        assertEquals(NO_ORDER_ITEMS_MSG, e.getMessage());
    }

    @Test
    public void testCreateOrder_ItemDoesNotExistInDatabase() throws OrderException {
        given(itemServiceMock.findAllWithIds(anyList())).willReturn(new ArrayList<>());

        OrderDTO orderDTO = createOrderDTOWithOrderItems();

        Exception e = assertThrows(OrderException.class, () -> orderService.createOrder(orderDTO));

        assertEquals(NO_ORDER_ITEM_DATABASE_MSG, e.getMessage());
    }

    @Test
    public void testCreateOrder_OderItemInvalidQuantity() throws OrderException {
        given(itemServiceMock.findAllWithIds(anyList())).willReturn(createItems());

        OrderDTO orderDTO = createOrderDTOWithOrderItemsInvalidQuantity();

        Exception e = assertThrows(OrderException.class, () -> orderService.createOrder(orderDTO));

        assertEquals(INVALID_QUANTITY_MSG, e.getMessage());
    }

    @Test
    public void testCreateOrder_OderItemInvalidPriority() throws OrderException {
        given(itemServiceMock.findAllWithIds(anyList())).willReturn(createItems());

        OrderDTO orderDTO = createOrderDTOWithOrderItemsInvalidPriority();

        Exception e = assertThrows(OrderException.class, () -> orderService.createOrder(orderDTO));

        assertEquals(INVALID_PRIORITY_MSG, e.getMessage());
    }

    @Test
    public void testCreateOrder_InvalidWaiterId() throws OrderException {
        given(itemServiceMock.findAllWithIds(anyList())).willReturn(createItems());
        OrderDTO orderDTO = createOrderDTOWithOrderItems();
        orderDTO.setWaiterId(null);

        Exception e = assertThrows(OrderException.class, () -> orderService.createOrder(orderDTO));

        assertEquals(INVALID_WAITER_MSG, e.getMessage());
    }

    @Test
    public void testCreateOrder_InvalidTableId() throws OrderException {
        given(itemServiceMock.findAllWithIds(anyList())).willReturn(createItems());
        OrderDTO orderDTO = createOrderDTOWithOrderItems();
        orderDTO.setTableId(null);

        Exception e = assertThrows(OrderException.class, () -> orderService.createOrder(orderDTO));

        assertEquals(INVALID_TABLE_MSG, e.getMessage());
    }

    @Test
    public void testCreateOrder_InvalidNote() throws OrderException {
        given(itemServiceMock.findAllWithIds(anyList())).willReturn(createItems());
        OrderDTO orderDTO = createOrderDTOWithOrderItems();
        orderDTO.setNote(create301CharString());

        Exception e = assertThrows(OrderException.class, () -> orderService.createOrder(orderDTO));

        assertEquals(INVALID_NOTE_MSG, e.getMessage());
    }

    @Test
    public void testCreateOrder_TableInUse() throws OrderException {
        Table table = new Table();
        table.setId(120L);
        Order order = new Order();
        List<Order> orders = new ArrayList<>();
        orders.add(order);

        given(itemServiceMock.findAllWithIds(anyList())).willReturn(createItems());
        given(tableServiceMock.findById(120L)).willReturn(table);
        given(orderRepositoryMock.findActiveOrderByTable(120L)).willReturn(orders);

        OrderDTO orderDTO = createOrderDTOWithOrderItems();
        orderDTO.setTableId(120L);

        Exception e = assertThrows(OrderException.class, () -> orderService.createOrder(orderDTO));

        assertEquals(TABLE_IN_USE_MSG, e.getMessage());
    }

    @Test
    public void testFindOneWithOrderItemsForUpdate() {
        Order order = new Order();
        order.setId(1L);
        OrderItem oi1 = new OrderItem();
        oi1.setStatus(OrderItemStatus.ORDERED);
        OrderItem oi2 = new OrderItem();
        oi2.setStatus(OrderItemStatus.IN_PROGRESS);
        OrderItem oi3 = new OrderItem();
        oi3.setStatus(OrderItemStatus.FINISHED);
        Set<OrderItem> ois = new HashSet<>();
        ois.add(oi1); ois.add(oi2); ois.add(oi3);
        order.setOrderItems(ois);

        given(orderRepositoryMock.findOneWithOrderItems(1L)).willReturn(order);

        Order o = orderService.findOneWithOrderItemsForUpdate(1L);

        verify(orderRepositoryMock, times(1)).findOneWithOrderItems(1L);
        assertEquals(Long.valueOf(1), o.getId());
        assertEquals(1, order.getOrderItems().size());
        order.getOrderItems().forEach(orderItem -> assertEquals(OrderItemStatus.ORDERED, orderItem.getStatus()));
    }

    @Test
    public void testFindOneWithOrderItemsForUpdate_InvalidOrder() {
        given(orderRepositoryMock.findOneWithOrderItems(-1L)).willReturn(null);

        Order o = orderService.findOneWithOrderItemsForUpdate(-1L);

        verify(orderRepositoryMock, times(1)).findOneWithOrderItems(-1L);
        assertNull(o);
    }

    @Test
    public void testUpdateOrder_ChangeQuantityAndPriority() throws OrderException {
        // Changing quantity from INIT_QUANTITY1 to INIT_QUANTITY2 and priority from 1 to 2
        Order order = createOrderWithOrderItemsForUpdate(1L);
        OrderItem orderItem = findOrderItemWithId(1L, order);
        OrderNotification on = new OrderNotification();
        on.setOrder(order);

        given(orderRepositoryMock.findOneWithOrderItems(1L)).willReturn(order);
        given(orderNotificationServiceMock.notifyOrderItemChange(order, orderItem, INIT_QUANTITY2, 2)).willReturn(on);

        OrderDTO orderDTO = createOrderDTOItemUpdate(1L);
        Order o = orderService.updateOrder(orderDTO);

        verify(orderRepositoryMock, times(1)).findOneWithOrderItems(1L);
        verify(orderNotificationServiceMock, times(0)).notifyOrderItemDeleted(any(Order.class), any(OrderItem.class));
        verify(orderNotificationServiceMock, times(1)).notifyOrderItemChange(order, orderItem, INIT_QUANTITY2, 2);
        assertEquals(1L, o.getId());
        for(OrderItem oi : o.getOrderItems()) {
            if(oi.getId() == 1L){
                assertEquals(INIT_QUANTITY2, oi.getQuantity());
                assertEquals(2, oi.getPriority());
            }
        }
        assertEquals(3, o.getOrderItems().size());
        assertEquals(order.getNote(), "Alora, ciao bella.");
    }

    @Test
    public void testUpdateOrder_InvalidQuantity() throws OrderException {
        // Changing quantity from INIT_QUANTITY1 to -1
        Order order = createOrderWithOrderItemsForUpdate(1L);
        OrderItem orderItem = findOrderItemWithId(1L, order);
        orderItem.getItem().setName(FOOD_NAME);
        OrderNotification on = new OrderNotification();
        on.setOrder(order);

        given(orderRepositoryMock.findOneWithOrderItems(1L)).willReturn(order);
        given(orderNotificationServiceMock.notifyOrderItemChange(order, orderItem, INIT_QUANTITY2, 2)).willReturn(on);

        OrderDTO orderDTO = createOrderDTOItemUpdate(1L);
        orderDTO.getOrderItems().get(0).setQuantity(-1);
        orderDTO.getOrderItems().get(0).setPriority(2);
        Exception e = assertThrows(OrderException.class, () -> orderService.updateOrder(orderDTO));

        verify(orderRepositoryMock, times(1)).findOneWithOrderItems(1L);
        verify(orderNotificationServiceMock, times(0)).notifyOrderItemDeleted(any(Order.class), any(OrderItem.class));
        verify(orderNotificationServiceMock, times(1)).notifyOrderItemChange(order, orderItem, -1, 2);
        assertEquals(INVALID_QUANTITY_MSG, e.getMessage());
    }

    @Test
    public void testUpdateOrder_InvalidPriorityDrink() throws OrderException {
        // Changing priority to -1 on drink - should not throw Exception
        Order order = createOrderWithOrderItemsForUpdate(3L);
        OrderItem orderItem = findOrderItemWithId(3L, order);
        OrderNotification on = new OrderNotification();
        on.setOrder(order);

        given(orderRepositoryMock.findOneWithOrderItems(3L)).willReturn(order);

        OrderDTO orderDTO = createOrderDTOItemUpdate(3L);
        orderDTO.getOrderItems().get(0).setQuantity(INIT_QUANTITY1);
        orderDTO.getOrderItems().get(0).setPriority(-5);
        orderDTO.getOrderItems().get(0).setId(3L);
        Order o = orderService.updateOrder(orderDTO);

        verify(orderRepositoryMock, times(1)).findOneWithOrderItems(3L);
        verify(orderNotificationServiceMock, times(0)).notifyOrderItemDeleted(any(Order.class), any(OrderItem.class));
        verify(orderNotificationServiceMock, times(0)).notifyOrderItemChange(order, orderItem, INIT_QUANTITY1, 0);

        for(OrderItem oi : o.getOrderItems()) {
            if(oi.getId() == 3L){
                assertEquals(INIT_QUANTITY1, oi.getQuantity());
                assertEquals(0, oi.getPriority());
            }
        }
    }

    @Test
    public void testUpdateOrder_InvalidOrderId() throws OrderException {
        given(orderRepositoryMock.findOneWithOrderItems(null)).willReturn(null);
        given(orderRepositoryMock.findOneWithOrderItems(anyLong())).willReturn(null);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(null);
        OrderDTO finalOrderDTO = orderDTO;
        Exception e1 = assertThrows(OrderException.class, () -> orderService.updateOrder(finalOrderDTO));

        orderDTO = new OrderDTO();
        orderDTO.setId(-1L);
        OrderDTO finalOrderDTO1 = orderDTO;
        Exception e2 = assertThrows(OrderException.class, () -> orderService.updateOrder(finalOrderDTO1));

        assertEquals(INVALID_ORDER_ID_MSG, e1.getMessage());
        assertEquals(INVALID_ORDER_ID_MSG, e2.getMessage());
    }

    @Test
    public void testUpdateOrder_NoOrderItems() throws OrderException {
        Order order = new Order();
        order.setId(1L);
        order.setOrderItems(new HashSet<>());

        given(orderRepositoryMock.findOneWithOrderItems(1L)).willReturn(order);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(1L);
        Exception e = assertThrows(OrderException.class, () -> orderService.updateOrder(orderDTO));

        assertEquals(NO_ORDER_ITEMS_UPDATE_MSG, e.getMessage());
    }

    @Test
    public void testUpdateOrder_InvalidNote() throws OrderException {
        Order order = new Order();
        order.setId(1L);
        order.setOrderItems(new HashSet<>());

        given(orderRepositoryMock.findOneWithOrderItems(1L)).willReturn(order);

        OrderDTO orderDTO = createOrderDTOItemsAdd(1L);
        orderDTO.setNote(create301CharString());
        Exception e = assertThrows(OrderException.class, () -> orderService.updateOrder(orderDTO));

        assertEquals(INVALID_NOTE_MSG, e.getMessage());
    }

    @Test
    public void testUpdateOrder_NotChangeableOrderItem() throws OrderException {
        Order order = new Order();
        order.setId(1L);
        order.setOrderItems(new HashSet<>());
        order.getOrderItems().add(createFoodOrderItem(3L, OrderItemStatus.IN_PROGRESS, INIT_QUANTITY1, 0));

        given(orderRepositoryMock.findOneWithOrderItems(1L)).willReturn(order);
        OrderDTO orderDTO = createOrderDTOItemUpdate(1L);

        Exception e = assertThrows(OrderException.class, () -> orderService.updateOrder(orderDTO));

        assertEquals(NOT_CHANGEABLE_ORDER_ITEM_MSG, e.getMessage());
    }

    @Test
    public void testUpdateOrder_Delete() throws OrderException {
        // Deleting order item with id 1 from order
        Order order = createOrderWithOrderItemsForUpdate(1L);
        OrderItem orderItem = findOrderItemWithId(1L, order);
        OrderNotification on = new OrderNotification();
        on.setOrder(order);

        given(orderRepositoryMock.findOneWithOrderItems(1L)).willReturn(order);
        given(orderNotificationServiceMock.notifyOrderItemChange(order, orderItem, INIT_QUANTITY2, 2)).willReturn(on);
        given(orderNotificationServiceMock.notifyOrderItemDeleted(order, orderItem)).willReturn(on);

        OrderDTO orderDTO = createOrderDTOItemDelete(1L);
        Order o = orderService.updateOrder(orderDTO);

        verify(orderRepositoryMock, times(1)).findOneWithOrderItems(1L);
        verify(orderNotificationServiceMock, times(1)).notifyOrderItemDeleted(order, orderItem);
        verify(orderNotificationServiceMock, times(0)).notifyOrderItemChange(any(Order.class), any(OrderItem.class), anyInt(), anyInt());
        assertEquals(1L, o.getId());
        assertEquals(2, o.getOrderItems().size());
        assertEquals(o.getNote(), "Alora, ciao bella.");
    }

    @Test
    public void testUpdateOrder_AddItems() throws OrderException {
        Order order = createOrderWithOrderItemsForUpdate(1L);
        List<Item> items = new ArrayList<>();
        items.add(createDrinkItem(1L));
        items.add(createFoodItem(2L));

        given(orderRepositoryMock.findOneWithOrderItems(1L)).willReturn(order);
        given(itemServiceMock.findAllWithIds(anyList())).willReturn(items);

        OrderDTO orderDTO = createOrderDTOItemsAdd(1L);
        Order o = orderService.updateOrder(orderDTO);

        verify(orderRepositoryMock, times(1)).findOneWithOrderItems(1L);
        verify(orderNotificationServiceMock, times(0)).notifyOrderItemChange(any(Order.class), any(OrderItem.class), anyInt(), anyInt());
        verify(orderNotificationServiceMock, times(0)).notifyOrderItemDeleted(any(Order.class), any(OrderItem.class));
        assertEquals(Long.valueOf(1), o.getId());
        assertEquals(5, o.getOrderItems().size());
        assertEquals(o.getNote(), "Alora, ciao bella.");
    }

    @Test
    public void testFinishOrder() {
        Order order = createOrderForFinish(1L);
        given(orderRepositoryMock.findOneWithOrderItems(1L)).willReturn(order);

        Order o = orderService.finishOrder(1L);

        verify(orderNotificationServiceMock, times(1)).deleteOrderNotifications(order);
        verify(orderRepositoryMock, times(1)).save(order);
        assertEquals(Double.valueOf(1800.0), o.getProfit());
    }

    @Test
    public void testFinishOrder_InvalidId() {
        given(orderRepositoryMock.findOneWithOrderItems(-1L)).willReturn(null);

        Order o = orderService.finishOrder(-1L);

        assertNull(o);
    }

    private Order createOrderForFinish(Long id) {
        Order order = new Order();
        order.setId(id);

        Set<OrderItem> orderItems = new HashSet<>();
        OrderItem oi1 = createOrderItemForFinish(1L, PRICE1, 10); oi1.setStatus(OrderItemStatus.FINISHED);
        OrderItem oi2 = createOrderItemForFinish(2L, PRICE1, 10); oi2.setStatus(OrderItemStatus.ORDERED);
        OrderItem oi3 = createOrderItemForFinish(3L, PRICE2, 5);  oi3.setStatus(OrderItemStatus.IN_PROGRESS);
        OrderItem oi4 = createOrderItemForFinish(4L, PRICE3, 3);  oi4.setStatus(OrderItemStatus.DELIVERED);
        orderItems.add(oi1); orderItems.add(oi2); orderItems.add(oi3); orderItems.add(oi4);

        order.setOrderItems(orderItems);
        return order;
    }

    private OrderItem createOrderItemForFinish(Long id, double price, int quantity) {
        OrderItem oi = new OrderItem();
        oi.setId(id);
        oi.setPrice(price);
        oi.setQuantity(quantity);

        Food food = new Food();
        food.setId(id);
        food.setCost(price - 100);
        oi.setItem(food);

        return oi;
    }

    private Order createOrderWithOrderItemsForUpdate(Long id) {
        Order order = new Order();
        order.setId(id);
        order.setOrderItems(new HashSet<>());
        order.getOrderItems().add(createFoodOrderItem(1L, OrderItemStatus.ORDERED, INIT_QUANTITY1, 1));
        order.getOrderItems().add(createFoodOrderItem(2L, OrderItemStatus.IN_PROGRESS, INIT_QUANTITY2, 2));
        order.getOrderItems().add(createDrinkOrderItem(3L, OrderItemStatus.ORDERED, INIT_QUANTITY1, DRINK_PRIORITY));
        order.getOrderItems().add(createDrinkOrderItem(4L, OrderItemStatus.FINISHED, INIT_QUANTITY2, DRINK_PRIORITY));
        order.getOrderItems().add(createDrinkOrderItem(5L, OrderItemStatus.ORDERED, INIT_QUANTITY1, DRINK_PRIORITY));
        order.setNote("Ciao bella.");

        return order;
    }

    private OrderItem findOrderItemWithId(Long id, Order order) {
        for(OrderItem oi : order.getOrderItems()) {
            if(oi.getId() == id)
                return oi;
        }
        return null;
    }

    private OrderDTO createOrderDTOItemUpdate(Long id) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(id);
        orderDTO.setNote("Alora, ciao bella.");
        orderDTO.setOrderItems(new ArrayList<>());
        orderDTO.getOrderItems().add(createOrderItemDTOForUpdate(1L, INIT_QUANTITY2, 2));

        return orderDTO;
    }

    private OrderDTO createOrderDTOItemDelete(Long id) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(id);
        orderDTO.setNote("Alora, ciao bella.");
        orderDTO.setOrderItems(new ArrayList<>());
        orderDTO.getOrderItems().add(createOrderItemDTOForUpdate(1L, DELETE_QUANTITY, 2));

        return orderDTO;
    }

    private OrderDTO createOrderDTOItemsAdd(Long id) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(id);
        orderDTO.setNote("Alora, ciao bella.");
        orderDTO.setOrderItems(new ArrayList<>());
        orderDTO.getOrderItems().add(createOrderItemDTOForAdding(1L, INIT_QUANTITY1, -1));
        orderDTO.getOrderItems().add(createOrderItemDTOForAdding(2L, INIT_QUANTITY2, -1));

        return orderDTO;
    }

    private OrderItemOrderCreationDTO createOrderItemDTOForUpdate(Long id, int quantity, int priority) {
        OrderItemOrderCreationDTO oi = new OrderItemOrderCreationDTO();
        oi.setId(id);
        oi.setQuantity(quantity);
        oi.setPriority(priority);

        return oi;
    }

    private OrderItemOrderCreationDTO createOrderItemDTOForAdding(Long id, int quantity, int priority) {
        OrderItemOrderCreationDTO oi = new OrderItemOrderCreationDTO();
        oi.setId(null);
        oi.setItemId(id);
        oi.setQuantity(quantity);
        oi.setPriority(priority);

        return oi;
    }

    private OrderItem createFoodOrderItem(Long id, OrderItemStatus status, int quantity, int priority) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(id);
        Food food = new Food();
        food.setId(id);

        orderItem.setItem(food);
        orderItem.setStatus(status);
        orderItem.setQuantity(quantity);
        orderItem.setPriority(priority);

        return orderItem;
    }

    private Food createFoodItem(Long id) {
        Food f = new Food();
        f.setId(id);
        f.setName(FOOD_NAME);
        f.setCurrentPrice(PRICE1);
        f.setType(FoodType.APPETIZER);

        return f;
    }

    private Drink createDrinkItem(Long id) {
        Drink d = new Drink();
        d.setId(id);
        d.setName(DRINK_NAME);
        d.setCurrentPrice(PRICE1);

        return d;
    }

    private OrderItem createDrinkOrderItem(Long id, OrderItemStatus status, int quantity, int priority) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(id);
        Drink drink = new Drink();
        drink.setId(id);

        orderItem.setItem(drink);
        orderItem.setStatus(status);
        orderItem.setQuantity(quantity);
        orderItem.setPriority(priority);

        return orderItem;
    }

    private OrderDTO createBlankOrderDTO() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(null);
        orderDTO.setWaiterId(1L);
        orderDTO.setTableId(1L);

        orderDTO.setCreatedAt(null);
        orderDTO.setStatus(null);
        orderDTO.setNote(NOTE);

        orderDTO.setOrderItems(new ArrayList<>());

        return orderDTO;
    }

    private OrderDTO createOrderDTOWithOrderItems() {
        OrderDTO orderDTO = createBlankOrderDTO();

        OrderItemOrderCreationDTO oi1DTO = new OrderItemOrderCreationDTO(null, 1L, INIT_QUANTITY1, PRICE1, 1);
        OrderItemOrderCreationDTO oi2DTO = new OrderItemOrderCreationDTO(null, 2L, INIT_QUANTITY1, PRICE2, 2);
        List<OrderItemOrderCreationDTO> ois = new ArrayList<>();
        ois.add(oi1DTO);
        ois.add(oi2DTO);
        orderDTO.setOrderItems(ois);

        return orderDTO;
    }

    private OrderDTO createOrderDTOWithOrderItemsInvalidQuantity() {
        OrderDTO orderDTO = createBlankOrderDTO();

        OrderItemOrderCreationDTO oi1DTO = new OrderItemOrderCreationDTO(null, 1L, INVALID_QUANTITY, PRICE1, 1);
        OrderItemOrderCreationDTO oi2DTO = new OrderItemOrderCreationDTO(null, 2L, INIT_QUANTITY1, PRICE2, 2);
        List<OrderItemOrderCreationDTO> ois = new ArrayList<>();
        ois.add(oi1DTO);
        ois.add(oi2DTO);
        orderDTO.setOrderItems(ois);

        return orderDTO;
    }

    private OrderDTO createOrderDTOWithOrderItemsInvalidPriority() {
        OrderDTO orderDTO = createBlankOrderDTO();

        OrderItemOrderCreationDTO oi1DTO = new OrderItemOrderCreationDTO(null, 1L, INIT_QUANTITY1, PRICE1, INVALID_PRIORITY);
        OrderItemOrderCreationDTO oi2DTO = new OrderItemOrderCreationDTO(null, 2L, INIT_QUANTITY1, PRICE2, 2);
        List<OrderItemOrderCreationDTO> ois = new ArrayList<>();
        ois.add(oi1DTO);
        ois.add(oi2DTO);
        orderDTO.setOrderItems(ois);

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

    private String create301CharString() {
        StringBuilder sb = new StringBuilder();
        sb.append("a".repeat(302));
        return sb.toString();
    }

}