package com.app.RestaurantApp.drinks;

import com.app.RestaurantApp.category.Category;
import com.app.RestaurantApp.category.CategoryService;
import com.app.RestaurantApp.drinks.dto.DrinkDTO;
import com.app.RestaurantApp.food.Food;
import com.app.RestaurantApp.food.FoodRepository;
import com.app.RestaurantApp.food.dto.FoodDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrinkServiceImpl implements DrinkService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DrinkRepository drinkRepository;

    @Override
    public Drink findOne(Long id) {
        return drinkRepository.findById(id).orElse(null);
    }

    @Override
    public Drink saveDrink(DrinkDTO drinkDTO) {
        Drink drink = new Drink(drinkDTO);
        if (drinkDTO.getCategory() != null) {
            Category category = categoryService.findOne(drinkDTO.getCategory().getId());
            if (category == null)
                // need to make category first
                category = categoryService.insertCategory(drinkDTO.getCategory());
            drink.setCategory(category);
        }
        drinkRepository.save(drink);
        return drink;
    }

    @Override
    public void deleteDrink(Drink drink) {
        drinkRepository.delete(drink);
    }
}
