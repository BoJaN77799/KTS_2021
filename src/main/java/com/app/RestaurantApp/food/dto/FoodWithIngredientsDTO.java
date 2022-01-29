package com.app.RestaurantApp.food.dto;

import com.app.RestaurantApp.category.dto.CategoryDTO;
import com.app.RestaurantApp.enums.ItemType;
import com.app.RestaurantApp.food.Food;
import com.app.RestaurantApp.ingredient.dto.IngredientDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;

public class FoodWithIngredientsDTO {

    private Long foodId;
    private HashSet<IngredientDTO> ingredients;

    public FoodWithIngredientsDTO() {}

    public FoodWithIngredientsDTO(Long foodId, HashSet<IngredientDTO> ingredients) {
        this.foodId = foodId;
        this.ingredients = ingredients;
    }

    public HashSet<IngredientDTO> getIngredients() {
        return ingredients;
    }

    public void setIngredients(HashSet<IngredientDTO> ingredients) {
        this.ingredients = ingredients;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }
}
