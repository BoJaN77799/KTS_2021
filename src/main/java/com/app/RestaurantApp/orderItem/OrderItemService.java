package com.app.RestaurantApp.orderItem;

import com.app.RestaurantApp.orderItem.dto.OrderItemChangeStatusDTO;

import java.util.List;

public interface OrderItemService {

    OrderItem changeStatus(OrderItemChangeStatusDTO orderItemChangeStatusDTO) throws OrderItemException;

    void delete(OrderItem orderItem);

    void deleteAll(List<OrderItem> orderItems);

}
