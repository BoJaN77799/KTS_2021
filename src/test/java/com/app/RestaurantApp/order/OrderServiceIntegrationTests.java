package com.app.RestaurantApp.order;

import com.app.RestaurantApp.notifications.OrderNotificationRepository;
import com.app.RestaurantApp.order.dto.OrderDTO;
import com.app.RestaurantApp.orderItem.OrderItem;
import com.app.RestaurantApp.orderItem.OrderItemRepository;
import com.app.RestaurantApp.orderItem.dto.OrderItemOrderCreationDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.app.RestaurantApp.order.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test @Transactional
    public void testUpdateOrder_ChangeQuantityAndPriority() throws OrderException {
        int notificationsSize = orderItemRepository.findAll().size();
        OrderDTO orderDTO = createOrderDTOItemUpdate(1L, 1L);

        Order order = orderService.updateOrder(orderDTO);
        OrderItem orderItem = findOrderItem(order, 1L);

        assertEquals(INIT_QUANTITY1, orderItem.getQuantity());
        assertEquals(1, orderItem.getPriority());
        assertEquals(notificationsSize, orderItemRepository.findAll().size());
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

    private OrderItem findOrderItem(Order order, Long id) {
        for(OrderItem orderItem : order.getOrderItems()) {
            if(orderItem.getId() == id)
                return orderItem;
        }
        return null;
    }

}
