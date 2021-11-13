package com.app.RestaurantApp.drinks.dto;

import com.app.RestaurantApp.category.dto.CategoryDTO;
import com.app.RestaurantApp.drinks.Drink;

public class DrinkWithPriceDTO extends DrinkDTO{

    Double price;

    public DrinkWithPriceDTO() {
    }

    public DrinkWithPriceDTO(Long id, String name, Double cost, String description, String image, CategoryDTO categoryDTO, double volume) {
        super(id, name, cost, description, image, categoryDTO, volume);
    }

    public DrinkWithPriceDTO(Drink drink){
        super(drink);
        this.price = drink.getCurrentPrice();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
