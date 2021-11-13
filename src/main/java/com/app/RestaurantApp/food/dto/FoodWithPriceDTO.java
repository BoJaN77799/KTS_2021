package com.app.RestaurantApp.food.dto;

import com.app.RestaurantApp.category.dto.CategoryDTO;
import com.app.RestaurantApp.food.Food;
import com.app.RestaurantApp.price.Price;

public class FoodWithPriceDTO extends FoodDTO{

    Double price;

    public FoodWithPriceDTO() { }

    public FoodWithPriceDTO(Long id, String name, Double cost, String description, String image, CategoryDTO category,
                            String recipe, Integer timeToMake, String foodType, Double price) {
        super(id, name, cost, description, image, category, recipe, timeToMake, foodType);
        this.price = price;
    }

    public FoodWithPriceDTO(Food food) {
        super(food);
        this.price = food.getCurrentPrice();
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
