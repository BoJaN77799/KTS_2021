package com.app.RestaurantApp.drinks;


import com.app.RestaurantApp.drinks.dto.DrinkDTO;
import com.app.RestaurantApp.drinks.dto.DrinkSearchDTO;
import com.app.RestaurantApp.drinks.dto.DrinkWithPriceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/drinks")
public class DrinkController {

    @Autowired
    private DrinkService drinkService;

    @GetMapping(consumes = "application/json")
    public ResponseEntity<List<DrinkWithPriceDTO>> getDrinksWithPrice(@RequestBody DrinkSearchDTO drinkSearchDTO, Pageable pageable) {

        List<DrinkWithPriceDTO> drinksDTO = drinkService.getDrinksWithPrice(drinkSearchDTO, pageable);

        return new ResponseEntity<>(drinksDTO, HttpStatus.OK);
    }

}
