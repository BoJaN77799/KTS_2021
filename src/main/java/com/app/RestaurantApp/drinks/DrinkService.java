package com.app.RestaurantApp.drinks;

import com.app.RestaurantApp.drinks.dto.DrinkDTO;
import com.app.RestaurantApp.drinks.dto.DrinkSearchDTO;
import com.app.RestaurantApp.item.ItemException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DrinkService {

    Page<Drink> getDrinksWithPrice(DrinkSearchDTO searchDTO, Pageable pageable);

    Drink findOne(Long id);

    Drink saveDrink(DrinkDTO drinkDTO) throws ItemException, DrinkException;

    void deleteDrink(Drink drink);

    List<Drink> findAll();

    List<String> findAllDrinkCategories();
}
