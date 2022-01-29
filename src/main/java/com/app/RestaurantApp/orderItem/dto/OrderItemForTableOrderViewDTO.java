package com.app.RestaurantApp.orderItem.dto;

import com.app.RestaurantApp.orderItem.OrderItem;

public class OrderItemForTableOrderViewDTO {
    private Long id;
    private Integer quantity;
    private Double price;
    private String status;
    private String name;

    public OrderItemForTableOrderViewDTO(){

    }

    public OrderItemForTableOrderViewDTO(OrderItem orderItem){
        this.id = orderItem.getId();
        this.quantity = orderItem.getQuantity();
        this.price = orderItem.getPrice();
        this.status = orderItem.getStatus().toString();
        this.name = orderItem.getItem().getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
