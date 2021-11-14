package com.app.RestaurantApp.food;

import com.app.RestaurantApp.food.FoodService;
import com.app.RestaurantApp.food.dto.FoodDTO;
import com.app.RestaurantApp.food.dto.FoodSearchDTO;
import com.app.RestaurantApp.food.dto.FoodWithPriceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping(consumes = "application/json")
    public ResponseEntity<List<FoodWithPriceDTO>> getFoodWithPrice(@RequestBody FoodSearchDTO foodSearchDTO, Pageable pageable) {

        List<FoodWithPriceDTO> foodsDTO = foodService.getFoodWithPrice(foodSearchDTO, pageable);

        return new ResponseEntity<>(foodsDTO, HttpStatus.OK);
    }
}