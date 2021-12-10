package com.app.RestaurantApp.order;

import com.app.RestaurantApp.order.dto.OrderDTO;
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

    @Test @Transactional
    public void testCreateOrder() throws OrderException {
        int ordersSize = orderRepository.findAll().size();
        int orderItemsSize = orderItemRepository.findAll().size();

        OrderDTO orderDTO = createOrderDTOWithOrderItems();
        orderService.createOrder(orderDTO);

        assertEquals(ordersSize + 1, orderRepository.findAll().size());
        assertEquals(orderItemsSize + 2, orderItemRepository.findAll().size());
    }

    public void testUpdateOrder() throws OrderException {

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

}
