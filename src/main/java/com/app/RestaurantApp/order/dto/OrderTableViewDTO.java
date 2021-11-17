package com.app.RestaurantApp.order.dto;

import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.orderItem.OrderItem;
import com.app.RestaurantApp.orderItem.dto.OrderItemForTableOrderViewDTO;
import com.app.RestaurantApp.orderItem.dto.OrderItemOrderCreationDTO;

import java.util.ArrayList;
import java.util.List;

public class OrderTableViewDTO {

    private Long id;
    private List<OrderItemForTableOrderViewDTO> orderItems;

    public OrderTableViewDTO() {
        super();
    }

    public OrderTableViewDTO(Order order){
        id = order.getId();
        orderItems = new ArrayList<>();
        for(OrderItem orderItem : order.getOrderItems()){
            orderItems.add(new OrderItemForTableOrderViewDTO(orderItem));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItemForTableOrderViewDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemForTableOrderViewDTO> orderItems) {
        this.orderItems = orderItems;
    }
}
