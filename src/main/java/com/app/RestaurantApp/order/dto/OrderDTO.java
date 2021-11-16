package com.app.RestaurantApp.order.dto;


import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.orderItem.OrderItem;
import com.app.RestaurantApp.orderItem.dto.OrderItemOrderCreationDTO;

import java.util.ArrayList;
import java.util.List;

public class OrderDTO extends SimpleOrderDTO {

    private List<OrderItemOrderCreationDTO> orderItems;

    public OrderDTO() {
        super();
    }

    public OrderDTO(Order order){
        super(order);
        orderItems = new ArrayList<>();
        for(OrderItem orderItem : order.getOrderItems()){
            orderItems.add(new OrderItemOrderCreationDTO(orderItem));
        }
    }

    public List<OrderItemOrderCreationDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemOrderCreationDTO> orderItems) {
        this.orderItems = orderItems;
    }
}
