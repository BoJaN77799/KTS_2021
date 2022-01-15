package com.app.RestaurantApp.drinks;

import com.app.RestaurantApp.drinks.dto.DrinkDTO;
import com.app.RestaurantApp.drinks.dto.DrinkSearchDTO;
import com.app.RestaurantApp.item.ItemException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.app.RestaurantApp.category.Category;
import com.app.RestaurantApp.category.CategoryService;

import java.util.List;

@Service
public class DrinkServiceImpl implements DrinkService {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DrinkRepository drinkRepository;

    @Override
    public Page<Drink> getDrinksWithPrice(DrinkSearchDTO searchDTO, Pageable pageable) {
        Page<Drink> drinksPage;

        String name = (searchDTO != null) ? searchDTO.getName() : null;
        name = (name == null || name.equals("")) ? "ALL" : name;

        String category = (searchDTO != null) ? searchDTO.getCategory() : null;
        category = (category == null || category.equals("")) ? "ALL" : category;

        drinksPage = drinkRepository.findAllWithPriceByCriteria(name, category, pageable);
        return drinksPage;
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
                category = categoryService.insertCategory(new Category(drinkDTO.getCategory()));
            drink.setCategory(category);
        }
        drinkRepository.save(drink);
        return drink;
    }

    @Override
    public void deleteDrink(Drink drink) {
        drinkRepository.delete(drink);
    }

    @Override
    public List<Drink> findAll() {
        return drinkRepository.findAll();
    }
}
