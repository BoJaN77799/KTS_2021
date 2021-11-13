package com.app.RestaurantApp.drinks;

import com.app.RestaurantApp.drinks.dto.DrinkDTO;
import com.app.RestaurantApp.food.Food;
import com.app.RestaurantApp.food.dto.FoodDTO;

public interface DrinkService {

    Drink findOne(Long id);

    Drink saveDrink(DrinkDTO drinkDTO);

    void deleteDrink(Drink drink);
}
