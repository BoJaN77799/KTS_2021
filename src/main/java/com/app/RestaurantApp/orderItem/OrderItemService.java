package com.app.RestaurantApp.orderItem;

import com.app.RestaurantApp.orderItem.dto.OrderItemChangeStatusDTO;

public interface OrderItemService {

    OrderItem changeStatus(OrderItemChangeStatusDTO orderItemChangeStatusDTO);

    void delete(OrderItem orderItem);

}
