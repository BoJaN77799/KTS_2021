package com.app.RestaurantApp.drinks;

import com.app.RestaurantApp.drinks.dto.DrinkDTO;
import com.app.RestaurantApp.drinks.dto.DrinkSearchDTO;
import com.app.RestaurantApp.drinks.dto.DrinkWithPriceDTO;
import com.app.RestaurantApp.item.ItemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
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
    public List<DrinkWithPriceDTO> getDrinksWithPrice(DrinkSearchDTO searchDTO, Pageable pageable) {
        List<Drink> drinks = new ArrayList<>();

        String name = (searchDTO != null) ? searchDTO.getName() : null;
        name = (name != null) ? name : "ALL";

        String category = (searchDTO != null) ? searchDTO.getCategory() : null;
        category = (category != null) ? category : "ALL";

        drinks = drinkRepository.findAllWithPriceByCriteria(name, category, pageable);

        List<DrinkWithPriceDTO> drinksDTO = new ArrayList<>();
        drinks.forEach(drink -> drinksDTO.add(new DrinkWithPriceDTO(drink)));

        return drinksDTO;
    }

    public Drink findOne(Long id) {
        return drinkRepository.findById(id).orElse(null);
    }

    @Override
    public Drink saveDrink(DrinkDTO drinkDTO) throws DrinkException, ItemException {
        Drink drink = new Drink(drinkDTO);
        DrinkUtils.CheckDrinkInfo(drink);
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
