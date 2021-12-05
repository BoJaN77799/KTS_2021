package com.app.RestaurantApp.order;

import com.app.RestaurantApp.orderItem.OrderItem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@DataJpaTest
public class OrderRepositoryIntegrationTests {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testFindOneWithOrderItems() {
        Order order;
        List<OrderItem> orderItems;

        order = orderRepository.findOneWithOrderItems(1L);
        assertNotNull(order);
        assertEquals(Long.valueOf(1), order.getId());
        assertEquals(2, order.getOrderItems().size());
        orderItems = sortHashSet(order.getOrderItems());
        assertEquals(Long.valueOf(1), orderItems.get(0).getItem().getId());
        assertEquals(Long.valueOf(1), orderItems.get(0).getId());
        assertEquals(Long.valueOf(2), orderItems.get(1).getItem().getId());
        assertEquals(Long.valueOf(2), orderItems.get(1).getId());

        order = orderRepository.findOneWithOrderItems(3L);
        assertNotNull(order);
        assertEquals(Long.valueOf(3), order.getId());
        orderItems = sortHashSet(order.getOrderItems());
        assertEquals(Long.valueOf(1), orderItems.get(0).getItem().getId());
        assertEquals(Long.valueOf(5), orderItems.get(0).getId());
        assertEquals(Long.valueOf(8), orderItems.get(1).getItem().getId());
        assertEquals(Long.valueOf(6), orderItems.get(1).getId());

        order = orderRepository.findOneWithOrderItems(-1L);
        assertNull(order);
    }

    private List<OrderItem> sortHashSet(Set<OrderItem> orderItems) {
        List<OrderItem> orderItemsList = new ArrayList<>(orderItems);
        orderItemsList.sort((oi1, oi2) -> (int) (oi1.getId() - oi2.getId()));
        return orderItemsList;
    }
}
