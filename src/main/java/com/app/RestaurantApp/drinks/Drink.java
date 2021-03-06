package com.app.RestaurantApp.drinks;

import com.app.RestaurantApp.drinks.dto.DrinkDTO;
import com.app.RestaurantApp.enums.FoodType;
import com.app.RestaurantApp.food.dto.FoodDTO;
import com.app.RestaurantApp.item.Item;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Drink extends Item {

    @Column(nullable = false)
    private double volume;

    public Drink() {
    }

    public Drink(DrinkDTO drink) {
        super(drink);
        this.volume = drink.getVolume();
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
