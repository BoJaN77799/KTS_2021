package com.app.RestaurantApp.ingredient.dto;

import com.app.RestaurantApp.ingredient.Ingredient;

public class IngredientDTO {
    private String name;
    private boolean allergen;

    public IngredientDTO() {}

    public IngredientDTO(String name, boolean allergen) {
        this.name = name;
        this.allergen = allergen;
    }

    public IngredientDTO(Ingredient ingredient) {
        this(ingredient.getName(), ingredient.isAllergen());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAllergen() {
        return allergen;
    }

    public void setAllergen(boolean allergen) {
        this.allergen = allergen;
    }
}
