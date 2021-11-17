package com.app.RestaurantApp.orderItem;

import com.app.RestaurantApp.enums.OrderItemStatus;

public class OrderItemUtils {

    public static void checkOrderItemChangeStatusInfo(OrderItem orderItem, String newStatus) throws OrderItemException {
        if(orderItem == null) {
            throw new OrderItemException("Invalid order item sent from front!");
        }

        OrderItemStatus newOrderItemStatus = OrderItemStatus.IN_PROGRESS;
        try {
            newOrderItemStatus = OrderItemStatus.valueOf(newStatus);
        }
        catch (Exception e) {
            throw new OrderItemException("Invalid order item status sent from front!");
        }

        if(!(isValidTransition(orderItem.getStatus(), newOrderItemStatus))) {
            throw new OrderItemException("Invalid order item status change (from: " + orderItem.getStatus().toString() +
                    " to: " + newStatus + ")!");
        }

    }

    private static boolean isValidTransition(OrderItemStatus status, OrderItemStatus newStatus) {
        if(status == OrderItemStatus.ORDERED && newStatus == OrderItemStatus.IN_PROGRESS) {
            return true;
        }
        else if(status == OrderItemStatus.IN_PROGRESS && newStatus == OrderItemStatus.FINISHED) {
            return true;
        }
        else if(status == OrderItemStatus.FINISHED && newStatus == OrderItemStatus.DELIVERED) {
            return true;
        }

        return false;
    }
}
