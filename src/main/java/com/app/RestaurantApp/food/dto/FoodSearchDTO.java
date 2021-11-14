package com.app.RestaurantApp.food.dto;

public class FoodSearchDTO {

    private String name;

    private String category;

    private String type;

    public FoodSearchDTO() { }

    public FoodSearchDTO(String name, String category, String type) {
        this.name = name;
        this.category = category;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
