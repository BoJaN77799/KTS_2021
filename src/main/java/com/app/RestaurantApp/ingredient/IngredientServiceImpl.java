package com.app.RestaurantApp.ingredient;

import com.app.RestaurantApp.food.Food;
import com.app.RestaurantApp.food.FoodRepository;
import com.app.RestaurantApp.food.FoodService;
import com.app.RestaurantApp.food.FoodServiceImpl;
import com.app.RestaurantApp.food.dto.FoodWithIngredientsDTO;
import com.app.RestaurantApp.ingredient.dto.IngredientDTO;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService{

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private FoodService foodService;

    @Override
    public List<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }

    @Override
    public Food saveIngredientsToFood(FoodWithIngredientsDTO foodWithIngredientsDTO) {
        Food food = foodService.findOne(foodWithIngredientsDTO.getFoodId());
        HashSet<Ingredient> ingredients = new HashSet<>();
        for (IngredientDTO ingredient : foodWithIngredientsDTO.getIngredients()) {
            ingredientRepository.findById(ingredient.getId()).ifPresent(ingredients::add);
        }
        food.setIngredients(ingredients);
        return foodService.saveFood(food);
    }
}
