package com.app.RestaurantApp.notifications;

public class Constants {

    public static final String NEW_ORDER_MESSAGE = "New order from table 1.";
    public static final int NEW_PRIORITY = 2;
    public static final int NEW_QUANTITY = 10;

    public static final int NEW_PRIORITY_DEF = -1;

    public static final int OLD_PRIORITY = 1;
    public static final int OLD_QUANTITY = 5;

    public static final String FOOD_NAME = "Hrana";
    public static final String DRINK_NAME = "Pice";

    public static final String ORDER_ITEM_CHANGE_MESSAGE = "Hrana from order #1, on table number 1:" +
                                                           "\n-Quantity changed from 5 to 10." +
                                                           "\n-Priority changed from 1 to 2.";

    public static final String PRIORITY_CHANGE_DEF_MSG = "Hrana from order #1, on table number 1:\n-Priority changed from 1 to default.";

    public static final String ORDER_ITEM_DELETED_MSG = "Hrana has been deleted from order #1, on table number 1";

    public static final String ORDER_ITEM_FOOD_FINISHED_MSG = "Hrana from order #1 is finished and ready to deliver to table 1.";
    public static final String ORDER_ITEM_DRINK_FINISHED_MSG = "Pice from order #1 is finished and ready to deliver to table 1.";




}
