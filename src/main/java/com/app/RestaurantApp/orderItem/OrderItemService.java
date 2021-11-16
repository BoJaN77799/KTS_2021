package com.app.RestaurantApp.orderItem;

import com.app.RestaurantApp.orderItem.dto.OrderItemChangeStatusDTO;

import java.util.List;

public interface OrderItemService {

    OrderItem changeStatus(OrderItemChangeStatusDTO orderItemChangeStatusDTO);

    void delete(OrderItem orderItem);

    void deleteAll(List<OrderItem> orderItems);

}
