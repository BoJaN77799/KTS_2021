package com.app.RestaurantApp.food;

import com.app.RestaurantApp.food.dto.FoodDTO;
import com.app.RestaurantApp.food.dto.FoodSearchDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FoodService {

    List<FoodDTO> getFood(FoodSearchDTO searchDTO, Pageable pageable);

}
