package com.app.RestaurantApp.order;

import com.app.RestaurantApp.order.dto.OrderDTO;

public interface OrderService {

    Order createOrder(OrderDTO orderDTO);

    Order findOne(Long id);

    Order findOneWithOrderItems(Long id);

    Order findOneWithOrderItemsForUpdate(Long id);

    Order updateOrder(OrderDTO orderDTO);

    Order finishOrder(Long id);
}
