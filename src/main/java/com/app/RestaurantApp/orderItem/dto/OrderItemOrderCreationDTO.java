package com.app.RestaurantApp.orderItem.dto;

import com.app.RestaurantApp.orderItem.OrderItem;

public class OrderItemOrderCreationDTO {

    private Long id;
    private Long itemId;
    private Integer quantity;
    private Double price;
    private Integer priority;

    public OrderItemOrderCreationDTO() { }

    public OrderItemOrderCreationDTO(Long id, Long itemId, Integer quantity, Double price, Integer priority) {
        this.id = id;
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
        this.priority = priority;
    }

    public OrderItemOrderCreationDTO(OrderItem orderItem){
        this(orderItem.getId(), orderItem.getItem().getId(), orderItem.getQuantity(),  orderItem.getPrice(), orderItem.getPriority());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}
