package com.app.RestaurantApp.table.dto;

import com.app.RestaurantApp.enums.OrderItemStatus;
import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.orderItem.OrderItem;
import com.app.RestaurantApp.table.Table;

import java.util.Optional;

public class TableWaiterDTO {

    private Long id;

    private double x;

    private double y;

    private boolean occupied;

    private String orderStatus;  //new, in progress, ready, finishable (ako su sva jela donesena i moze da se zavrsi porudzbina)

    public TableWaiterDTO() {
    }

    public TableWaiterDTO(Table table, boolean hasActiveOrders) {
        this.id = table.getId();
        this.x = table.getX();
        this.y = table.getY();
        if (hasActiveOrders) {
            this.occupied = true;
            Optional<Order> order = table.getOrders().stream().findFirst();
            order.ifPresent(value -> this.orderStatus = determineOrderStatus(value));
        }else{
            this.occupied = false;
            this.orderStatus = "TABLE FREE";
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    private String determineOrderStatus(Order order){
        boolean orderIsInProgress = false;
        boolean orderIsNew = false;

        for (OrderItem orderItem : order.getOrderItems()) {
            if (orderItem.getStatus() == OrderItemStatus.FINISHED){
                return "READY"; //ready to pickup
            }
            if (orderItem.getStatus() == OrderItemStatus.IN_PROGRESS  && !orderIsInProgress){
                orderIsInProgress = true;
            }
            if (orderItem.getStatus() == OrderItemStatus.ORDERED  && !orderIsNew){
                orderIsNew = true;
            }
        }
        if (orderIsInProgress)
            return "IN PROGRESS";
        if (orderIsNew)
            return "NEW";
        return "FINISHABLE";
    }
}
