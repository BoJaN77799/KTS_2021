package com.app.RestaurantApp.category;


import com.app.RestaurantApp.category.dto.CategoryDTO;
import com.app.RestaurantApp.food.Food;
import com.app.RestaurantApp.food.dto.FoodDTO;

public interface CategoryService {

    Category findOne(Long id);
    Category insertCategory(CategoryDTO categoryDTO);
}
