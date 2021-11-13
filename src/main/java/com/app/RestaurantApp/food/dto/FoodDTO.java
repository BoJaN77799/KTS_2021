package com.app.RestaurantApp.food.dto;

import com.app.RestaurantApp.category.dto.CategoryDTO;
import com.app.RestaurantApp.food.Food;
import com.app.RestaurantApp.item.dto.ItemDTO;

public class FoodDTO extends ItemDTO {

    private String recipe;
    private Integer timeToMake;
    private String type;

    public FoodDTO() {}

    public FoodDTO(Long id, String name, Double cost, String description, String image, CategoryDTO category, String recipe, Integer timeToMake, String foodType) {
        super(id, name, cost, description, image, category);
        this.recipe = recipe;
        this.timeToMake = timeToMake;
        this.type = foodType;
    }

    public FoodDTO(Food food) {
        super(food.getId(), food.getName(), food.getCost(), food.getDescription(), food.getImage(), new CategoryDTO(food.getCategory()));
        this.recipe = food.getRecipe();
        this.timeToMake = food.getTimeToMake();
        this.type = food.getType().toString();
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public Integer getTimeToMake() {
        return timeToMake;
    }

    public void setTimeToMake(Integer timeToMake) {
        this.timeToMake = timeToMake;
    }

    public String getType() {
        return type;
    }

    public void setFoodType(String type) {
        this.type = type;
    }

}
