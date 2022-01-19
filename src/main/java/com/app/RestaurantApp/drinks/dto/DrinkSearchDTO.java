package com.app.RestaurantApp.drinks.dto;

public class DrinkSearchDTO {

    private String name;

    private String category;

    private String menu;

    public DrinkSearchDTO() { }

    public DrinkSearchDTO(String name, String category, String menu) {
        this.name = name;
        this.category = category;
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

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }
}
