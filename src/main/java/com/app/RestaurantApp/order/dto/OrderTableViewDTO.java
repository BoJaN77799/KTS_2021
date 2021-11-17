package com.app.RestaurantApp.order.dto;

import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.orderItem.OrderItem;
import com.app.RestaurantApp.orderItem.dto.OrderItemForTableOrderViewDTO;
import com.app.RestaurantApp.orderItem.dto.OrderItemOrderCreationDTO;

import java.util.ArrayList;
import java.util.List;

public class OrderTableViewDTO {
    private List<OrderItemForTableOrderViewDTO> orderItems;

    public OrderTableViewDTO() {
        super();
    }

    public OrderTableViewDTO(Order order){
        orderItems = new ArrayList<>();
        for(OrderItem orderItem : order.getOrderItems()){
            orderItems.add(new OrderItemForTableOrderViewDTO(orderItem));
        }
    }

    public List<OrderItemForTableOrderViewDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemForTableOrderViewDTO> orderItems) {
        this.orderItems = orderItems;
    }
}
