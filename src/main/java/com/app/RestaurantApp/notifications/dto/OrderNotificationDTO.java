package com.app.RestaurantApp.notifications.dto;

import com.app.RestaurantApp.notifications.OrderNotification;

public class OrderNotificationDTO {

    private Long id;

    private String message;

    private Long tableId;

    private Long orderId;

    public OrderNotificationDTO() { }

    public OrderNotificationDTO(Long id, String message, Long tableId, Long orderId) {
        this.id = id;
        this.message = message;
        this.tableId = tableId;
        this.orderId = orderId;
    }

    public OrderNotificationDTO(OrderNotification on) {
        this(on.getId(), on.getMessage(), on.getOrder().getTable().getId(), on.getOrder().getId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
