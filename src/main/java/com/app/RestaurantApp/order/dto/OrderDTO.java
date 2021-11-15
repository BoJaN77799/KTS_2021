package com.app.RestaurantApp.order.dto;


import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.orderItem.OrderItem;
import com.app.RestaurantApp.orderItem.dto.OrderItemOrderCreationDTO;

import java.util.ArrayList;
import java.util.List;

public class OrderDTO {

    private Long id;
    private String status;
    private Long createdAt;
    private String note;
    private Long tableId;
    private Long waiterId;
    private Long cookId;
    private Long barmenId;
    private List<OrderItemOrderCreationDTO> orderItems;

    public OrderDTO() { }

    public OrderDTO(Order order){
        this.id = order.getId();
        this.status = order.getStatus().toString();
        this.createdAt = order.getCreatedAt();
        this.note = order.getNote();
        this.tableId = (order.getTable() != null) ? order.getTable().getId() : null;
        this.waiterId = (order.getWaiter() != null) ? order.getWaiter().getId() : null;
        this.cookId = (order.getCook() != null) ? order.getCook().getId() : null;
        this.barmenId = (order.getBarmen() != null) ? order.getBarmen().getId() : null;
        orderItems = new ArrayList<>();
        for(OrderItem orderItem : order.getOrderItems()){
            orderItems.add(new OrderItemOrderCreationDTO(orderItem));
        }
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

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Long getWaiterId() {
        return waiterId;
    }

    public void setWaiterId(Long waiterId) {
        this.waiterId = waiterId;
    }

    public List<OrderItemOrderCreationDTO> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemOrderCreationDTO> orderItems) {
        this.orderItems = orderItems;
    }

    public Long getCookId() {
        return cookId;
    }

    public void setCookId(Long cookId) {
        this.cookId = cookId;
    }

    public Long getBarmenId() {
        return barmenId;
    }

    public void setBarmenId(Long barmenId) {
        this.barmenId = barmenId;
    }
}
