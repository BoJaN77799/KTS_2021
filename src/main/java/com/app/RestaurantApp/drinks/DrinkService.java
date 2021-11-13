package com.app.RestaurantApp.drinks;

import com.app.RestaurantApp.drinks.dto.DrinkDTO;
import com.app.RestaurantApp.drinks.dto.DrinkSearchDTO;
import com.app.RestaurantApp.drinks.dto.DrinkWithPriceDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DrinkService {

    List<DrinkWithPriceDTO> getDrinksWithPrice(DrinkSearchDTO searchDTO, Pageable pageable);

}
