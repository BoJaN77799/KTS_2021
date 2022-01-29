package com.app.RestaurantApp.orderItem;

public class Constants {

    public static final String INVALID_OI_MSG = "Invalid order item sent from front!";
    public static final String INVALID_OI_STATUS_MSG = "Invalid order item status sent from front!";
    public static final String INVALID_TRANSITION_MSG1 = "Invalid order item status change " +
            "(from: FINISHED to: IN_PROGRESS)!";
    public static final String INVALID_TRANSITION_MSG2 = "Invalid order item status change " +
            "(from: IN_PROGRESS to: DELIVERED)!";
    public static final String PRIORITY_DENIED = "Denied - There is a order item with a higher priority.";

    public static final String OI_SUCC_CHANGED = "Order item status successfully changed.";
}
