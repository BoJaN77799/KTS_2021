package com.app.RestaurantApp.order;

import com.app.RestaurantApp.enums.FoodType;
import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.food.Food;
import com.app.RestaurantApp.orderItem.OrderItem;

public class OrderUtils {

    public static void checkBasicOrderInfo(Order order) throws OrderException {

        if(order == null) {
            throw new OrderException("Invalid order sent from front!");
        }
        else if(order.getWaiter() == null || order.getWaiter().getUserType() != UserType.WAITER){
            throw new OrderException("Invalid waiter id sent from front!");
        }
        else if(order.getTable() == null){
            throw new OrderException("Invalid table id sent from front!");
        }
        else if(order.getNote().length() > 300) {
            throw new OrderException("Maximum characters for note is 300!");
        }

    }

    public static void checkNoteLength(String note) throws OrderException {
        if(note.length() > 300) {
            throw new OrderException("Maximum characters for note is 300!");
        }
    }

    public static void checkOrderItemsQuantity(Order order) throws OrderException {
        StringBuilder sb = new StringBuilder();

        for(OrderItem oi : order.getOrderItems()) {
            if(oi.getQuantity() <= 0) {
                String msg = "Quantity of " + oi.getItem().getName() + " is '" + oi.getQuantity() + "'." +
                        " Should be greater than zero.";

                sb.append(msg);
                sb.append("\n");
            }
        }

        if(!sb.isEmpty()) {
            throw new OrderException(sb.toString().trim());
        }
    }

    public static void checkOrderItemsPriority(Order order) throws OrderException {
        StringBuilder sb = new StringBuilder();

        for(OrderItem oi : order.getOrderItems()) {
            if(oi.getPriority() < -1 || oi.getPriority() > 2) {
                String msg = "Priority of " + oi.getItem().getName() + " is '" + oi.getPriority() + "'." +
                        " Should be less or equal than two or greater or equal than one (or default - not set).";

                sb.append(msg);
                sb.append("\n");
            }
        }

        if(!sb.isEmpty()) {
            throw new OrderException(sb.toString().trim());
        }
    }

}
