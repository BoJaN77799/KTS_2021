package com.app.RestaurantApp.order;

import com.app.RestaurantApp.order.dto.OrderDTO;
import com.app.RestaurantApp.orderItem.dto.OrderItemSimpleDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface OrderService {

    Order createOrder(OrderDTO orderDTO);

    Order findOne(Long id);

    Order findOneWithOrderItems(Long id);

}
