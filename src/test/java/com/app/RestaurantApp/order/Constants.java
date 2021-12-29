package com.app.RestaurantApp.order;

public class Constants {
    public static final String NOTE = "Some note";
    public static final String FOOD_NAME = "Food";
    public static final String DRINK_NAME = "Drink";

    public static final Integer INIT_QUANTITY1 = 5;
    public static final Integer INIT_QUANTITY2 = 3;
    public static final Integer DELETE_QUANTITY = 0;
    public static final Integer INVALID_QUANTITY = -1;
    public static final Integer INVALID_PRIORITY = 50;
    public static final Integer DRINK_PRIORITY = 0;

    public static final Double PRICE1 = 500.0;
    public static final Double PRICE2 = 300.0;
    public static final Double PRICE3 = 400.0;

    public static final String INVALID_ORDER_ID_MSG = "Invalid order id sent from front!";
    public static final String NO_ORDER_ITEMS_MSG = "Order does not have order items!";
    public static final String NO_ORDER_ITEMS_UPDATE_MSG = "Order does not have order items for update!";

    public static final String NO_ORDER_ITEM_DATABASE_MSG = "One of items selected for order does not exist in database!";

    public static final String INVALID_QUANTITY_MSG = "Quantity of " + FOOD_NAME + " is '" + INVALID_QUANTITY + "'." + " Should be greater than zero.";

    public static final String INVALID_PRIORITY_MSG = "Priority of " + FOOD_NAME + " is '" + INVALID_PRIORITY + "'." +
                                                      " Should be less or equal two or greater or equal than one (or default - not set or '-1').";

    public static final String INVALID_WAITER_MSG = "Invalid waiter id sent from front!";
    public static final String INVALID_TABLE_MSG = "Invalid table id sent from front!";
    public static final String INVALID_NOTE_MSG = "Maximum characters for note is 300!";
    public static final String TABLE_IN_USE_MSG = "Table in use!";

    public static final String NOT_CHANGEABLE_ORDER_ITEM_MSG = "Order item with id: 1 does not exist in order or cannot be changed!";

    public static final int PAGEABLE_PAGE = 0;
    public static final int PAGEABLE_SIZE = 4;
    public static final int PAGEABLE_TOTAL_ELEMENTS = 2;
    public static final String SEARCH_FIELD = "Dodik";
    public static final String ORDER_STATUS_IP = "IN_PROGRESS";


    public static final String COOK_EMAIL = "headcook@maildrop.cc";
    public static final String COOK_PWD = "cook";
    public static final String BARMAN_EMAIL = "barman@maildrop.cc";
    public static final String BARMAN_PWD = "barman";
    public static final String WAITER_EMAIL = "waiter@maildrop.cc";
    public static final String WAITER_PWD = "waiter";

    public static final String ORDER_CREATED = "Order successfully created.";
    public static final String ORDER_UPDATED = "Order successfully updated.";
    public static final String ORDER_FINISHED = "Order successfully finished.";
    public static final String ORDER_NOT_FOUND = "Order not found!";

    public static final Long ORDER_ID_FOOD = 1L;
    public static final Long ORDER_ID_DRINKS = 2L;
    public static final String ORDER_ITEM_FOOD_NAME_1 = "Supa";
    public static final String ORDER_ITEM_FOOD_NAME_2 = "Pohovani kackavalj";

    public static final String ORDER_ITEM_DRINK_NAME_1 = "Coca Cola";
    public static final String ORDER_ITEM_DRINK_NAME_2 = "Niksicko pivo";

    public static final Long COOK_ID = 4L;
    public static final Long BARMAN_ID = 5L;
}
