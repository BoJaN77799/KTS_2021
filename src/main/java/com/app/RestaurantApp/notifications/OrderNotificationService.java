package com.app.RestaurantApp.notifications;

import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.orderItem.OrderItem;

import java.util.Set;

public interface OrderNotificationService {

    void notifyNewOrder(Order order);

    void notifyOrderItemChange(Order order, OrderItem oldOrderItem, int newQuantity, boolean newPriority);

    void notifyOrderItemAdded(Order order, Set<OrderItem> orderItems);

    void notifyOrderItemDeleted(Order order, OrderItem orderItem);
}
