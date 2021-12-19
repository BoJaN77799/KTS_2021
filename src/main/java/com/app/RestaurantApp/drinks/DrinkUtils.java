package com.app.RestaurantApp.drinks;

import com.app.RestaurantApp.enums.FoodType;
import com.app.RestaurantApp.food.FoodException;
import com.app.RestaurantApp.item.ItemException;
import com.app.RestaurantApp.item.ItemUtils;

public class DrinkUtils extends ItemUtils {

    public static void CheckDrinkInfo(Drink drink) throws ItemException, DrinkException {
        CheckItemInfo(drink);
        if (drink.getVolume() <= 0) {
            throw new DrinkException("Drink volume cannot be less or equal zero!");
        }
    }
}
