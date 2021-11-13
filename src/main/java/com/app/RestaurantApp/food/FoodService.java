package com.app.RestaurantApp.food;

import com.app.RestaurantApp.food.dto.FoodDTO;
import com.app.RestaurantApp.food.dto.FoodSearchDTO;
import com.app.RestaurantApp.food.dto.FoodWithPriceDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FoodService {

    List<FoodWithPriceDTO> getFoodWithPrice(FoodSearchDTO searchDTO, Pageable pageable);

}
