package com.app.RestaurantApp.ingredient;

import com.app.RestaurantApp.ingredient.dto.IngredientDTO;
import com.app.RestaurantApp.item.Item;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean allergen;

    @ManyToMany(mappedBy = "ingredients")
    private Set<Item> items;

    public Ingredient() {}

    public Ingredient(String name, Boolean allergen){
        this.name = name;
        this.allergen = allergen;
    }

    public Ingredient(IngredientDTO ingredientDTO){
        this.name = ingredientDTO.getName();
        this.allergen = ingredientDTO.isAllergen();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Item> getItems(){ return this.items; }

    public void setItems(Set<Item> items) { this.items = items; }
}
