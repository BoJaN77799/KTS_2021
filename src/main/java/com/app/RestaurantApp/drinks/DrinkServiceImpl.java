package com.app.RestaurantApp.drinks;

import com.app.RestaurantApp.drinks.dto.DrinkDTO;
import com.app.RestaurantApp.drinks.dto.DrinkSearchDTO;
import com.app.RestaurantApp.drinks.dto.DrinkWithPriceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DrinkServiceImpl implements DrinkService{

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

}
