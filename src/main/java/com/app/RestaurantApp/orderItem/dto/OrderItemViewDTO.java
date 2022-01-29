package com.app.RestaurantApp.orderItem.dto;

import com.app.RestaurantApp.enums.OrderItemStatus;
import com.app.RestaurantApp.item.dto.ItemDTO;
import com.app.RestaurantApp.order.dto.SimpleOrderDTO;
import com.app.RestaurantApp.orderItem.OrderItem;

public class OrderItemViewDTO {

    private Long id;
    private int quantity;
    private OrderItemStatus status;
    private double price;
    private int priority;
    private SimpleOrderDTO order;
    private ItemDTO item;

    public OrderItemViewDTO() {}

    public OrderItemViewDTO(OrderItem orderItem) {
        this.id = orderItem.getId();
        this.quantity = orderItem.getQuantity();
        this.status = orderItem.getStatus();
        this.price = orderItem.getPrice();
        this.priority = orderItem.getPriority();
        this.order = new SimpleOrderDTO(orderItem.getOrder());
        this.item = new ItemDTO(orderItem.getItem());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public OrderItemStatus getStatus() {
        return status;
    }

    public void setStatus(OrderItemStatus status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public SimpleOrderDTO getOrder() {
        return order;
    }

    public void setOrder(SimpleOrderDTO order) {
        this.order = order;
    }

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }
}
