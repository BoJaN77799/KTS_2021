package com.app.RestaurantApp.orderItem;

import com.app.RestaurantApp.enums.OrderItemStatus;
import com.app.RestaurantApp.notifications.OrderNotificationService;
import com.app.RestaurantApp.orderItem.dto.OrderItemChangeStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemServiceImpl implements OrderItemService{

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderNotificationService orderNotificationService;

    @Override
    public OrderItem changeStatus(OrderItemChangeStatusDTO orderItemChangeStatusDTO) throws OrderItemException {
        OrderItem orderItem = orderItemRepository.findById(orderItemChangeStatusDTO.getId()).orElse(null);
        OrderItemUtils.checkOrderItemChangeStatusInfo(orderItem, orderItemChangeStatusDTO.getStatus());

        orderItem.setStatus(OrderItemStatus.valueOf(orderItemChangeStatusDTO.getStatus()));

        orderItemRepository.save(orderItem);
        if(orderItem.getStatus() == OrderItemStatus.FINISHED)
            orderNotificationService.notifyWaiterOrderItemStatusFinished(orderItem);

        return orderItem;
    }

    @Override
    public void delete(OrderItem orderItem) {
        orderItemRepository.delete(orderItem);
    }

    @Override
    public void deleteAll(List<OrderItem> orderItems) {
        orderItemRepository.deleteAll(orderItems);
    }


}
