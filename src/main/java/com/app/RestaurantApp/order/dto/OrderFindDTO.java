package com.app.RestaurantApp.order.dto;

import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.orderItem.OrderItem;
import com.app.RestaurantApp.orderItem.dto.OrderItemForTableOrderViewDTO;
import com.app.RestaurantApp.orderItem.dto.OrderItemOrderCreationDTO;
import com.app.RestaurantApp.orderItem.dto.OrderItemViewDTO;

import java.util.ArrayList;
import java.util.List;

public class OrderFindDTO extends SimpleOrderDTO {

    private List<OrderItemViewDTO> orderItems;

    public OrderFindDTO() {
        super();
    }

    public OrderFindDTO(Order order){
        super(order);
        orderItems = new ArrayList<>();
        for(OrderItem orderItem : order.getOrderItems()){
            orderItems.add(new OrderItemViewDTO(orderItem));
        }
    }

    public List<OrderItemViewDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemViewDTO> orderItems) {
        this.orderItems = orderItems;
    }
}
