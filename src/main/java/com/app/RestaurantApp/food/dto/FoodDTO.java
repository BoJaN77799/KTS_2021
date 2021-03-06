package com.app.RestaurantApp.food.dto;

import com.app.RestaurantApp.category.dto.CategoryDTO;
import com.app.RestaurantApp.enums.ItemType;
import com.app.RestaurantApp.food.Food;
import com.app.RestaurantApp.item.dto.ItemDTO;
import org.springframework.web.multipart.MultipartFile;

public class FoodDTO extends ItemDTO {

    private String recipe;
    private Integer timeToMake;
    private String type;
    private MultipartFile multipartImageFile;

    public FoodDTO() {}

    public FoodDTO(Long id, String name, Double cost, String description, String image, CategoryDTO category, ItemType itemType, boolean deleted, String recipe, Integer timeToMake, String type) {
        super(id, name, cost, description, image, category.getName(), itemType, deleted);
        this.recipe = recipe;
        this.timeToMake = timeToMake;
        this.type = type;
    }

    public FoodDTO(Food food) {
        super(food.getId(), food.getName(), food.getCost(), food.getDescription(), food.getImage(), food.getCategory().getName(), food.getItemType(), food.isDeleted());
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

    public void setType(String type) {
        this.type = type;
    }

    public MultipartFile getMultipartImageFile() {
        return multipartImageFile;
    }

    public void setMultipartImageFile(MultipartFile multipartImageFile) {
        this.multipartImageFile = multipartImageFile;
    }
}
