package com.app.RestaurantApp.order;

import com.app.RestaurantApp.order.dto.OrderDTO;

import com.app.RestaurantApp.users.UserException;

import java.util.List;


public interface OrderService {

    Order createOrder(OrderDTO orderDTO);

    Order findOne(Long id);

    Order findOneWithOrderItems(Long id);

    Order findOneWithFood(Long id);

    List<Order> findAllNewWithFood();

    List<Order> findAllMyWithFood(Long id);

    Order findOneWithDrinks(Long id);

    List<Order> findAllNewWithDrinks();

    List<Order> findAllMyWithDrinks(Long id);

    void acceptOrder(Long id, String email) throws OrderException, UserException;

    Order findOneWithOrderItemsForUpdate(Long id);

    Order updateOrder(OrderDTO orderDTO);

    Order finishOrder(Long id);

    List<Order> findAllOrderInIntervalOfDates(Long dateFrom, Long dateTo);

}
