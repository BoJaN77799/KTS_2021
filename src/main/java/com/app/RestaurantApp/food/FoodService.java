package com.app.RestaurantApp.food;

import com.app.RestaurantApp.food.dto.FoodDTO;
import com.app.RestaurantApp.food.dto.FoodSearchDTO;
import com.app.RestaurantApp.food.dto.FoodWithPriceDTO;
import com.app.RestaurantApp.item.ItemException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FoodService {

    Page<Food> getFoodWithPrice(FoodSearchDTO searchDTO, Pageable pageable);

    Food findOne(Long id);

    Food saveFood(FoodDTO foodDTO) throws ItemException, FoodException;

    void deleteFood(Food food);
}
