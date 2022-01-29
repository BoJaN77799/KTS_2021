package com.app.RestaurantApp.order;

import com.app.RestaurantApp.enums.OrderItemStatus;
import com.app.RestaurantApp.enums.OrderStatus;
import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.notifications.OrderNotificationRepository;
import com.app.RestaurantApp.order.dto.OrderDTO;
import com.app.RestaurantApp.orderItem.OrderItem;
import com.app.RestaurantApp.orderItem.OrderItemRepository;
import com.app.RestaurantApp.orderItem.dto.OrderItemOrderCreationDTO;
import com.app.RestaurantApp.users.UserException;
import com.app.RestaurantApp.users.employee.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.app.RestaurantApp.order.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderServiceIntegrationTests {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderNotificationRepository orderNotificationRepository;

    @Test @Transactional
    public void testCreateOrder() throws OrderException {
        int ordersSize = orderRepository.findAll().size();
        int orderItemsSize = orderItemRepository.findAll().size();

        OrderDTO orderDTO = createOrderDTOWithOrderItems();
        orderService.createOrder(orderDTO);

        assertEquals(ordersSize + 1, orderRepository.findAll().size());
        assertEquals(orderItemsSize + 2, orderItemRepository.findAll().size());
    }

    @Test
    public void testAcceptOrder() throws UserException, OrderException {
        // Test invoke
        orderService.acceptOrder(1L, COOK_EMAIL);

        // Verifying
        Order order = orderService.findOne(1L);
        assertNotNull(order);
        assertEquals(COOK_EMAIL, order.getCook().getEmail());
        assertEquals(OrderStatus.IN_PROGRESS, order.getStatus());
    }

    @Test
    public void testAcceptOrder_OrderNotFound(){
        // Test invoke
        Exception exception = assertThrows(OrderException.class, () -> orderService.acceptOrder(INVALID_ORDER_ID, COOK_EMAIL));

        // Verifying
        assertNotNull(exception.getMessage());
        assertEquals(ORDER_NOT_FOUND, exception.getMessage());
    }

    @Test
    public void testAcceptOrder_UserNotFound(){
        // Test invoke
        Exception exception = assertThrows(UserException.class, () -> orderService.acceptOrder(ORDER_ID, INVALID_COOK_EMAIL));

        // Verifying
        assertNotNull(exception.getMessage());
        assertEquals(USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    public void testAcceptOrder_CookAlreadyAccepted(){
        // Test invoke
        Exception exception = assertThrows(OrderException.class, () -> orderService.acceptOrder(5L, COOK_EMAIL));

        // Verifying
        assertNotNull(exception.getMessage());
        assertEquals(COOK_ALREADY_ACCEPTED, exception.getMessage());
    }

    @Test
    public void testAcceptOrder_BarmanAlreadyAccepted(){
        // Test invoke
        Exception exception = assertThrows(OrderException.class, () -> orderService.acceptOrder(ORDER_ID, BARMAN_EMAIL));

        // Verifying
        assertNotNull(exception.getMessage());
        assertEquals(BARMAN_ALREADY_ACCEPTED, exception.getMessage());
    }

    @Test @Transactional
    public void testUpdateOrder_ChangeQuantityAndPriority() throws OrderException {
        int orderItemsSize = orderItemRepository.findAll().size();
        OrderDTO orderDTO = createOrderDTOItemUpdate(1L, 1L);

        Order order = orderService.updateOrder(orderDTO);
        OrderItem orderItem = findOrderItem(order, 1L);

        assertEquals(INIT_QUANTITY1, orderItem.getQuantity());
        assertEquals(1, orderItem.getPriority());
        assertEquals(orderItemsSize, orderItemRepository.findAll().size());
    }

    @Test @Transactional
    public void testUpdateOrder_ChangeQuantityAndPriorityInProgress() throws OrderException {
        int notificationsSize = orderNotificationRepository.findAll().size();
        OrderDTO orderDTO = createOrderDTOItemUpdate(3L, 5L);

        Order order = orderService.updateOrder(orderDTO);
        OrderItem orderItem = findOrderItem(order, 5L);

        assertEquals(INIT_QUANTITY1, orderItem.getQuantity());
        assertEquals(1, orderItem.getPriority());
        assertEquals(notificationsSize + 1, orderNotificationRepository.findAll().size());
    }

    @Test @Transactional
    public void testUpdateOrder_Delete() throws OrderException {
        int notificationsSize = orderNotificationRepository.findAll().size();
        int orderItemsSize = orderItemRepository.findAll().size();
        OrderDTO orderDTO = createOrderDTOItemUpdate(3L, 5L);
        orderDTO.getOrderItems().get(0).setQuantity(DELETE_QUANTITY);

        Order order = orderService.updateOrder(orderDTO);

        assertEquals(1, order.getOrderItems().size());
        assertEquals(notificationsSize + 1, orderNotificationRepository.findAll().size());
        assertEquals(orderItemsSize - 1, orderItemRepository.findAll().size());
    }

    @Test @Transactional
    public void testUpdateOrder_AddItems() throws OrderException {
        int notificationsSize = orderNotificationRepository.findAll().size();
        int orderItemsSize = orderItemRepository.findAll().size();
        OrderDTO orderDTO = createOrderDTOItemsAdd(3L);

        Order order = orderService.updateOrder(orderDTO);

        assertEquals(4, order.getOrderItems().size());
        assertEquals(notificationsSize + 2, orderNotificationRepository.findAll().size());
        assertEquals(orderItemsSize + 2, orderItemRepository.findAll().size());

    }

    @Test @Transactional
    public void testFinishOrder() {
        int notificationsSize = orderNotificationRepository.findAll().size();

        Order order = orderService.finishOrder(12L);

        assertEquals(OrderStatus.FINISHED, order.getStatus());
        assertEquals(Double.valueOf(0), order.getProfit());
        assertEquals(notificationsSize - 6, orderNotificationRepository.findAll().size());
    }

    @Test
    public void testFindOneWithOrderItemsForUpdate() {
        Order order = orderService.findOneWithOrderItemsForUpdate(13L);

        assertEquals(1, order.getOrderItems().size());
    }

    @Test
    public void testFindOneWithOrderItemsForUpdate_AllOrdered() {
        Order order = orderService.findOneWithOrderItemsForUpdate(12L);

        assertEquals(2, order.getOrderItems().size());
        for(OrderItem oi : order.getOrderItems()) {
            assertEquals(OrderItemStatus.ORDERED, oi.getStatus());
        }
    }

    @Test
    public void testSearchOrders() {
        Pageable pageableSetup = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        // Test invoke
        Page<Order> ordersPage = orderService.searchOrders(SEARCH_FIELD, ORDER_STATUS_IP, pageableSetup);

        // Verifying
        assertNotNull(ordersPage);
        assertEquals(4 , ordersPage.stream().toList().size());
    }

    @Test
    public void testFindOneWithFood(){

        // Test invoke
        Order order =  orderRepository.findOneWithFood(ORDER_ID_FOOD);

        // Verifying
        assertNotNull(order);
        assertEquals(2, order.getOrderItems().size());
        assertNotNull(order.getOrderItems().stream().filter(orderItem -> orderItem.getItem().getName().equals(ORDER_ITEM_FOOD_NAME_1)).findAny().orElse(null));
        assertNotNull(order.getOrderItems().stream().filter(orderItem -> orderItem.getItem().getName().equals(ORDER_ITEM_FOOD_NAME_2)).findAny().orElse(null));
    }

    @Test
    public void testFindAllNewWithFood() {

        Pageable pg = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);

        // Test invoke
        Page<Order> orders = orderRepository.findAllNewWithFood(pg);

        // Verifying
        assertNotNull(orders);
        assertEquals(2, orders.stream().toList().size());
    }

    @Test
    public void testFindAllMyWithFood() {
        Pageable pg = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);

        // Test invoke
        Page<Order> orders = orderRepository.findAllMyWithFood(COOK_ID, pg);

        // Verifying
        assertNotNull(orders);
        assertEquals(4, orders.stream().toList().size());
    }

    @Test
    public void testFindOneWithDrinks() {

        // Test invoke
        Order order = orderRepository.findOneWithDrinks(ORDER_ID_DRINKS);

        // Verifying
        assertNotNull(order);
        assertEquals(2, order.getOrderItems().size());
        assertNotNull(order.getOrderItems().stream().filter(orderItem -> orderItem.getItem().getName().equals(ORDER_ITEM_DRINK_NAME_1)).findAny().orElse(null));
        assertNotNull(order.getOrderItems().stream().filter(orderItem -> orderItem.getItem().getName().equals(ORDER_ITEM_DRINK_NAME_2)).findAny().orElse(null));
    }

    @Test
    public void testFindAllNewWithDrinks() {
        Pageable pg = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);

        // Test invoke
        Page<Order> orders = orderRepository.findAllNewWithDrinks(pg);

        // Verifying
        assertNotNull(orders);
        assertEquals(2, orders.stream().toList().size());
    }

    @Test
    public void testFindAllMyWithDrinks() {
        Pageable pg = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);

        // Test invoke
        Page<Order> orders = orderRepository.findAllMyWithDrinks(BARMAN_ID, pg);

        // Verifying
        assertNotNull(orders);
        assertEquals(3, orders.stream().toList().size());
    }

    private OrderDTO createOrderDTOItemsAdd(Long id) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(id);
        orderDTO.setNote("Alora, ciao bella.");
        orderDTO.setOrderItems(new ArrayList<>());
        orderDTO.getOrderItems().add(createOrderItemDTOForAdding(5L, INIT_QUANTITY1, -1));
        orderDTO.getOrderItems().add(createOrderItemDTOForAdding(6L, INIT_QUANTITY2, -1));

        return orderDTO;
    }

    private OrderDTO createOrderDTOWithOrderItems() {
        OrderDTO orderDTO = createBlankOrderDTO();

        OrderItemOrderCreationDTO oi1DTO = new OrderItemOrderCreationDTO(null, 1L, INIT_QUANTITY1, PRICE1, 1, DUMMY, ITF);
        OrderItemOrderCreationDTO oi2DTO = new OrderItemOrderCreationDTO(null, 2L, INIT_QUANTITY1, PRICE2, 2, DUMMY, ITF);
        List<OrderItemOrderCreationDTO> ois = new ArrayList<>();
        ois.add(oi1DTO);
        ois.add(oi2DTO);
        orderDTO.setOrderItems(ois);

        return orderDTO;
    }

    private OrderDTO createBlankOrderDTO() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(null);
        orderDTO.setWaiterId(3L);
        orderDTO.setTableId(120L);

        orderDTO.setCreatedAt(null);
        orderDTO.setStatus(null);
        orderDTO.setNote(NOTE);

        orderDTO.setOrderItems(new ArrayList<>());

        return orderDTO;
    }

    private OrderDTO createOrderDTOItemUpdate(Long orderId, Long itemId) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(orderId);
        orderDTO.setNote("Alora, ciao bella.");
        orderDTO.setOrderItems(new ArrayList<>());
        orderDTO.getOrderItems().add(createOrderItemDTOForUpdate(itemId, INIT_QUANTITY1, 1));

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

    private OrderItem findOrderItem(Order order, Long id) {
        for (OrderItem orderItem : order.getOrderItems()) {
            if (orderItem.getId() == id)
                return orderItem;
        }
        return null;
    }
}
