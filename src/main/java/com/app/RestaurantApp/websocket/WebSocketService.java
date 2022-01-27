package com.app.RestaurantApp.websocket;

import com.app.RestaurantApp.enums.NotificationType;
import com.app.RestaurantApp.notifications.OrderNotification;

import java.util.List;

public interface WebSocketService {

    void sendNotifications(List<OrderNotification> orderNotifications, NotificationType type);

}
