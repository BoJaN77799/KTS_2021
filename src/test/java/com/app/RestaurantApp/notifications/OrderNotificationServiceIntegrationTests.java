package com.app.RestaurantApp.notifications;
import com.app.RestaurantApp.drinks.Drink;
import com.app.RestaurantApp.food.Food;
import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.orderItem.OrderItem;
import com.app.RestaurantApp.table.Table;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.app.RestaurantApp.notifications.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderNotificationServiceIntegrationTests {

    @Autowired
    private OrderNotificationService orderNotificationService;

    @Autowired
    private OrderNotificationRepository orderNotificationRepository;

    @Test
    public void testNotifyNewOrder() {
        Order order = createOrder();

        List<OrderNotification> orderNotifications = orderNotificationService.notifyNewOrder(order);
        assertNotEquals(0, orderNotifications.size());
    }

    @Test @Transactional
    public void testDeleteOrderNotifications() {
        Order order = new Order();
        order.setId(12L);
        int size = orderNotificationRepository.findAll().size();

        orderNotificationService.deleteOrderNotifications(order);

        assertEquals(size -2, orderNotificationRepository.findAll().size());
    }

    @Test @Transactional
    public void testSaveAll() {
        int size = orderNotificationRepository.findAll().size();

        List<OrderNotification> ons = createOrderNotifications();
        orderNotificationService.saveAll(ons);

        assertEquals(size + 3, orderNotificationRepository.findAll().size());
    }

    private Order createOrder() {
        Order order = new Order();
        Table table = new Table();
        table.setId(1L);
        order.setTable(table);
        order.setOrderItems(new HashSet<>());
        order.getOrderItems().add(createFoodOrderItem(1L));
        order.getOrderItems().add(createDrinkOrderItem(2L));

        return order;
    }

    private OrderItem createFoodOrderItem(Long id) {
        Food food = new Food();
        food.setId(id);
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(food);
        orderItem.setId(1L);
        orderItem.setQuantity(OLD_QUANTITY);
        orderItem.setPriority(OLD_PRIORITY);

        return orderItem;
    }

    private OrderItem createDrinkOrderItem(Long id) {
        Drink drink = new Drink();
        drink.setId(id);
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(drink);
        orderItem.setId(1L);
        orderItem.setQuantity(OLD_QUANTITY);
        orderItem.setPriority(OLD_PRIORITY);

        return orderItem;
    }

    private List<OrderNotification> createOrderNotifications() {
        OrderNotification on1 = new OrderNotification();
        on1.setOrder(null);
        on1.setMessage("Novokreirana obavjest 1!");

        OrderNotification on2 = new OrderNotification();
        on2.setOrder(null);
        on2.setMessage("Novokreirana obavjest 2!");

        OrderNotification on3 = new OrderNotification();
        on3.setOrder(null);
        on3.setMessage("Novokreirana obavjest 3!");

        List<OrderNotification> ons = new ArrayList<OrderNotification>();
        ons.add(on1); ons.add(on2); ons.add(on3);
        return ons;
    }

}