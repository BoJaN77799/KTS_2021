package com.app.RestaurantApp.food.dto;

public class FoodSearchDTO {

    private String name;

    private String category;

    private String type;

    private String menu;

    public FoodSearchDTO() { }

    public FoodSearchDTO(String name, String category, String type, String menu) {
        this.name = name;
        this.category = category;
        this.type = type;
        this.menu = menu;
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

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }
}
