package com.app.RestaurantApp.ingredient;

import com.app.RestaurantApp.food.Food;
import com.app.RestaurantApp.food.dto.FoodWithIngredientsDTO;

import java.util.List;

public interface IngredientService {

    List<Ingredient> getAll();

    Food saveIngredientsToFood(FoodWithIngredientsDTO foodWithIngredientsDTO);
}
