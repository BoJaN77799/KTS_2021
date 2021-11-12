package com.app.RestaurantApp.food;

import com.app.RestaurantApp.food.dto.FoodDTO;

public interface FoodService {

    Food findOne(Long id);

    Food saveFood(FoodDTO foodDTO);

    void deleteFood(Food food);
}
