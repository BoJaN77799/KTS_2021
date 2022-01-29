package com.app.RestaurantApp.orderItem;

import com.app.RestaurantApp.enums.NotificationType;
import com.app.RestaurantApp.enums.OrderItemStatus;
import com.app.RestaurantApp.notifications.OrderNotification;
import com.app.RestaurantApp.notifications.OrderNotificationService;
import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.order.OrderService;
import com.app.RestaurantApp.orderItem.dto.OrderItemChangeStatusDTO;
import com.app.RestaurantApp.websocket.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderNotificationService orderNotificationService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private WebSocketService webSocketService;

    @Override
    public OrderItem changeStatus(OrderItemChangeStatusDTO orderItemChangeStatusDTO) throws OrderItemException {
        OrderItem orderItem = orderItemRepository.findById(orderItemChangeStatusDTO.getId()).orElse(null);
        // Is it valid transition of OrderItemStatus
        OrderItemUtils.checkOrderItemChangeStatusInfo(orderItem, orderItemChangeStatusDTO.getStatus());

        // Does anyone whose IN_PROGRESS have higher priority?
        if (OrderItemStatus.valueOf(orderItemChangeStatusDTO.getStatus()).equals(OrderItemStatus.FINISHED)) {
            checkPriority(orderItem, OrderItemStatus.IN_PROGRESS);
        }
        // Does anyone whose ORDERED have higher priority?
        if (OrderItemStatus.valueOf(orderItemChangeStatusDTO.getStatus()).equals(OrderItemStatus.IN_PROGRESS)) {
            checkPriority(orderItem, OrderItemStatus.ORDERED);
        }
        orderItem.setStatus(OrderItemStatus.valueOf(orderItemChangeStatusDTO.getStatus()));

        orderItemRepository.save(orderItem);
        if (orderItem.getStatus() == OrderItemStatus.FINISHED) {
            List<OrderNotification> orderNotifications = List.of(orderNotificationService.notifyWaiterOrderItemStatusFinished(orderItem));
            orderNotificationService.saveAll(orderNotifications);
            webSocketService.sendNotifications(orderNotifications, NotificationType.FINISH_ORDER_ITEM); // Slanje poruka na ws
        }

        return orderItem;
    }

    private void checkPriority(OrderItem orderItem, OrderItemStatus referencingStatus) throws OrderItemException {
        Order order = orderService.findOneWithOrderItems(orderItem.getOrder().getId());
        for (OrderItem orderItemInItem : order.getOrderItems()) {
            if (orderItemInItem.getStatus() == referencingStatus && orderItem.getPriority() < orderItemInItem.getPriority())
                throw new OrderItemException("Denied - There is a order item with a higher priority.");
        }

    }

    @Override
    public void delete(OrderItem orderItem) {
        orderItemRepository.delete(orderItem);
    }

    @Override
    public void deleteAll(List<OrderItem> orderItems) {
        orderItemRepository.deleteAll(orderItems);
    }

    @Override
    public void deliverOrderItem(Long id) throws OrderItemException {
        //todo testovi
        OrderItem orderItem = orderItemRepository.findById(id).orElse(null);
        if (orderItem == null)
            throw new OrderItemException("No order item with that id exists!");

        if (orderItem.getStatus() != OrderItemStatus.FINISHED)
            throw new OrderItemException("Can't deliver unfinished order!");

        orderItem.setStatus(OrderItemStatus.DELIVERED);
        orderItemRepository.save(orderItem);
    }
}
