package com.app.RestaurantApp.notifications;

import com.app.RestaurantApp.drinks.Drink;
import com.app.RestaurantApp.food.Food;
import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.orderItem.OrderItem;
import com.app.RestaurantApp.table.Table;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderNotificationServiceIntegrationTests {

    @Autowired
    private OrderNotificationService orderNotificationService;

    @Test
    public void testNotifyNewOrder() {
        Order order = createOrder();

        List<OrderNotification> orderNotification = orderNotificationService.notifyNewOrder(order);
        assertNotEquals(0, orderNotification.size());
    }

    @Test
    public void notifyOrderItemChange() {
        
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

        return orderItem;
    }

    private OrderItem createDrinkOrderItem(Long id) {
        Drink drink = new Drink();
        drink.setId(id);
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(drink);
        orderItem.setId(1L);

        return orderItem;
    }


}
