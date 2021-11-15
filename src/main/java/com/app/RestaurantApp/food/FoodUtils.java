package com.app.RestaurantApp.food;

import com.app.RestaurantApp.enums.FoodType;
import com.app.RestaurantApp.item.ItemException;
import com.app.RestaurantApp.item.ItemUtils;

public class FoodUtils extends ItemUtils {


    public static void CheckFoodInfo(Food food) throws FoodException, ItemException {
        CheckItemInfo(food);

        if (food.getRecipe() == null || food.getType() == null){
            throw new FoodException("Invalid food data sent from front! Null values in attributes!");
        }

        if (food.getRecipe().isBlank()) {
            throw new FoodException("Recipe cannot be blank");
        }

        if (food.getType() != FoodType.APPETIZER && food.getType() != FoodType.MAIN_DISH && food.getType() != FoodType.DESERT) {
            throw new FoodException("Food type is APPETIZER, MAIN_DISH or DESERT");
        }
    }
}
