package com.app.RestaurantApp.orderItem.dto;

import com.app.RestaurantApp.orderItem.OrderItem;

public class OrderItemChangeStatusDTO {

    private Long id;
    private String status;

    public OrderItemChangeStatusDTO() { }

    public OrderItemChangeStatusDTO(Long id, String status) {
        this.id = id;
        this.status = status;
    }

    public OrderItemChangeStatusDTO(OrderItem orderItem) {
        this(orderItem.getId(), orderItem.getStatus().toString());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
