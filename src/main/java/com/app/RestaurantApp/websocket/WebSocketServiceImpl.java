package com.app.RestaurantApp.websocket;

import com.app.RestaurantApp.notifications.OrderNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WebSocketServiceImpl implements WebSocketService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    public void sendNotifications(List<OrderNotification> orderNotifications) {

        for(OrderNotification on : orderNotifications) {
            Map<String, String> message = new HashMap<>();
            message.put("message", on.getMessage());
            message.put("id", on.getId().toString());
            message.put("orderId", on.getOrder().toString());
            message.put("tableId", on.getOrder().getTable().getId().toString());

            this.simpMessagingTemplate.convertAndSend(String.format("/socket-publisher/%d", on.getEmployee().getId()), message);
        }

    }

}
