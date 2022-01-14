package com.app.RestaurantApp.ingredient;

import com.app.RestaurantApp.food.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService{

    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public List<Ingredient> getAll() {
        return ingredientRepository.findAll();
    }
}
