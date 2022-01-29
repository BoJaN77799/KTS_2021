package com.app.RestaurantApp.order;

import com.app.RestaurantApp.order.dto.OrderDTO;

import com.app.RestaurantApp.users.UserException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface OrderService {

    Order createOrder(OrderDTO orderDTO) throws OrderException;

    Order findOne(Long id);

    Order findOneWithOrderItems(Long id);

    Order findOneWithFood(Long id);

    Page<Order> findAllNewWithFood(Pageable pageable);

    Page<Order> findAllMyWithFood(Long id, Pageable pageable);

    Order findOneWithDrinks(Long id);

    Page<Order> findAllNewWithDrinks(Pageable pageable);

    Page<Order> findAllMyWithDrinks(Long id, Pageable pageable);

    void acceptOrder(Long id, String email) throws OrderException, UserException;

    Order findOneWithOrderItemsForUpdate(Long id);

    Order updateOrder(OrderDTO orderDTO) throws OrderException;

    Order finishOrder(Long id);

    List<Order> findAllOrderInIntervalOfDates(Long dateFrom, Long dateTo);

    Page<Order> searchOrders(String searchField, String orderStatus, Pageable pageable);

    Order findOrderAtTable(Long tableID);
  
    List<Order> getOrdersByDate(long dateFrom, long dateTo);
  
}
