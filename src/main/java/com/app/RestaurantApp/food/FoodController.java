package com.app.RestaurantApp.food;

import com.app.RestaurantApp.ControllerUtils;
import com.app.RestaurantApp.food.FoodService;
import com.app.RestaurantApp.food.dto.FoodDTO;
import com.app.RestaurantApp.food.dto.FoodSearchDTO;
import com.app.RestaurantApp.food.dto.FoodWithPriceDTO;
import com.app.RestaurantApp.item.ItemException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/food")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<List<FoodWithPriceDTO>> getFoodWithPrice(FoodSearchDTO foodSearchDTO, Pageable pageable) {
        Page<Food> foodsPage = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        List<Food> foods = foodsPage.getContent();

        List<FoodWithPriceDTO> foodsDTO = new ArrayList<>();
        foods.forEach(food -> foodsDTO.add(new FoodWithPriceDTO(food)));

        HttpHeaders httpHeaderAttributes = ControllerUtils.createPageHeaderAttributes(foodsPage);
        return new ResponseEntity<>(foodsDTO, httpHeaderAttributes, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FoodDTO> findOne(@PathVariable Long id) {
        Food food = foodService.findOne(id);
        if (food == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new FoodDTO(food), HttpStatus.CREATED);
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasRole('HEAD_COOK')")
    public ResponseEntity<String> saveFood(@RequestBody FoodDTO foodDTO) {
        foodDTO.setDeleted(false);
        try {
            foodService.saveFood(foodDTO);
            return new ResponseEntity<>("Food successfully created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(consumes = "application/json")
    @PreAuthorize("hasRole('HEAD_COOK')")
    public ResponseEntity<String> updateFood(@RequestBody FoodDTO foodDTO) {
        try {
            Food food = foodService.findOne(foodDTO.getId());

            if (food == null) {
                return new ResponseEntity<>("Food cannot be null", HttpStatus.BAD_REQUEST);
            }
            foodService.saveFood(foodDTO);
            return new ResponseEntity<>("Food successfully updated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteFood(@PathVariable Long id) {

        Food food = foodService.findOne(id);

        if (food == null) {
            return new ResponseEntity<>("Food does not exist with requested ID",HttpStatus.BAD_REQUEST);
        }
        foodService.deleteFood(food);
        return new ResponseEntity<>("Food successfully deleted", HttpStatus.OK);
    }
}
