package com.app.RestaurantApp.notifications;

import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.orderItem.OrderItem;

import java.util.List;
import java.util.Set;

public interface OrderNotificationService {

    List<OrderNotification> notifyNewOrder(Order order);

    OrderNotification notifyOrderItemChange(Order order, OrderItem oldOrderItem, int newQuantity, int newPriority);

    List<OrderNotification> notifyOrderItemAdded(Order order, Set<OrderItem> orderItems);

    OrderNotification notifyOrderItemDeleted(Order order, OrderItem orderItem);

    void deleteOrderNotifications(Order order);

    OrderNotification notifyWaiterOrderItemStatusFinished(OrderItem orderItem);

    void saveAll(List<OrderNotification> orderNotifications);
}
