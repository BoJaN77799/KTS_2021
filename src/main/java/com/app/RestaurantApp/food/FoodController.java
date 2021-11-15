package com.app.RestaurantApp.food;

import com.app.RestaurantApp.food.FoodService;
import com.app.RestaurantApp.food.dto.FoodDTO;
import com.app.RestaurantApp.food.dto.FoodSearchDTO;
import com.app.RestaurantApp.food.dto.FoodWithPriceDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value = "/{id}")
    public ResponseEntity<FoodDTO> findOne(@PathVariable Long id) {
        Food food = foodService.findOne(id);
        if (food == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new FoodDTO(food), HttpStatus.CREATED);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<FoodDTO> saveFood(@RequestBody FoodDTO foodDTO) {
        foodDTO.setDeleted(false);
        Food food = foodService.saveFood(foodDTO);
        return new ResponseEntity<>(new FoodDTO(food), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<FoodDTO> updateFood(@RequestBody FoodDTO foodDTO) {

        Food food = foodService.findOne(foodDTO.getId());

        if (food == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        food = foodService.saveFood(foodDTO);
        return new ResponseEntity<>(new FoodDTO(food), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteFood(@PathVariable Long id) {

        Food food = foodService.findOne(id);

        if (food == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        foodService.deleteFood(food);
        return new ResponseEntity<>("Food successfully deleted.", HttpStatus.OK);
    }
}
