package com.app.RestaurantApp.drinks.dto;

import com.app.RestaurantApp.category.dto.CategoryDTO;
import com.app.RestaurantApp.drinks.Drink;
import com.app.RestaurantApp.enums.ItemType;
import com.app.RestaurantApp.item.dto.ItemDTO;

public class DrinkDTO extends ItemDTO {

    private double volume;

    public DrinkDTO() {}

    public DrinkDTO(double volume) {
        this.volume = volume;
    }

    public DrinkDTO(Long id, String name, Double cost, String description, String image, String category, ItemType itemType, boolean deleted, Double volume) {
        super(id, name, cost, description, image, category, itemType, deleted);
        this.volume = volume;
    }

    public DrinkDTO(Drink drink) {
        super(drink.getId(), drink.getName(), drink.getCost(), drink.getDescription(), drink.getImage(), drink.getCategory().getName(), drink.getItemType(), drink.isDeleted());
        this.volume = drink.getVolume();
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }
}
