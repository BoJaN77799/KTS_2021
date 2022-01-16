package com.app.RestaurantApp.ingredient.dto;

import com.app.RestaurantApp.ingredient.Ingredient;

public class IngredientDTO {
    private Long id;
    private String name;
    private boolean allergen;

    public IngredientDTO() {}

    public IngredientDTO(Long id, String name, boolean allergen) {
        this.id = id;
        this.name = name;
        this.allergen = allergen;
    }

    public IngredientDTO(Ingredient ingredient) {
        this(ingredient.getId(), ingredient.getName(), ingredient.isAllergen());
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

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
