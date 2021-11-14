package com.app.RestaurantApp.drinks.dto;

public class DrinkSearchDTO {

    private String name;

    private String category;

    public DrinkSearchDTO() { }

    public DrinkSearchDTO(String name, String category) {
        this.name = name;
        this.category = category;
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
}
