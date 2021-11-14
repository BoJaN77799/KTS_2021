package com.app.RestaurantApp.order;

import com.app.RestaurantApp.order.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    Order createOrder(OrderDTO orderDTO);

    Order findOne(Long id);

    Order findOneWithOrderItems(Long id);

    Order findOneWithFood(Long id);

    List<Order> findAllNewWithFood();

    List<Order> findAllMyWithFood(Long id);
}
