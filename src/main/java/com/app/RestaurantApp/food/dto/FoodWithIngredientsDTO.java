package com.app.RestaurantApp.food.dto;

import com.app.RestaurantApp.category.dto.CategoryDTO;
import com.app.RestaurantApp.enums.ItemType;
import com.app.RestaurantApp.food.Food;
import com.app.RestaurantApp.ingredient.dto.IngredientDTO;

import java.util.HashSet;

public class FoodWithIngredientsDTO extends FoodDTO {

    private HashSet<IngredientDTO> ingredients;

    public FoodWithIngredientsDTO() {
    }

    public FoodWithIngredientsDTO(Food food) {
        super(food);
        ingredients.addAll(food.getIngredients().stream().map(IngredientDTO::new).toList());
    }

    public FoodWithIngredientsDTO(Long id, String name, Double cost, String description, String image, CategoryDTO category, ItemType itemType, boolean deleted, String recipe, Integer timeToMake, String type, HashSet<IngredientDTO> ingredients) {
        super(id, name, cost, description, image, category, itemType, deleted, recipe, timeToMake, type);
        this.ingredients = ingredients;
    }

    public HashSet<IngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(HashSet<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }
}
