package com.app.RestaurantApp.food;

import com.app.RestaurantApp.enums.FoodType;
import com.app.RestaurantApp.item.Item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Food extends Item {

    @Column( name="recipe", nullable = false)
    private String recipe;

    @Column( name="time_to_make", nullable = false)
    private int timeToMake;

    @Enumerated(EnumType.STRING)
    @Column( name="food_type", nullable = false)
    private FoodType type;

    public Food() {
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public int getTimeToMake() {
        return timeToMake;
    }

    public void setTimeToMake(int timeToMake) {
        this.timeToMake = timeToMake;
    }

    public FoodType getType() {
        return type;
    }

    public void setType(FoodType type) {
        this.type = type;
    }
}
