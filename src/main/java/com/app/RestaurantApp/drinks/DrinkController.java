package com.app.RestaurantApp.drinks;


import com.app.RestaurantApp.ControllerUtils;
import com.app.RestaurantApp.drinks.dto.DrinkDTO;
import com.app.RestaurantApp.drinks.dto.DrinkSearchDTO;
import com.app.RestaurantApp.drinks.dto.DrinkWithPriceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/drinks")
public class DrinkController {

    @Autowired
    private DrinkService drinkService;

    @GetMapping
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<List<DrinkWithPriceDTO>> getDrinksWithPrice(DrinkSearchDTO drinkSearchDTO, Pageable pageable) {
        Page<Drink> drinksPage = drinkService.getDrinksWithPrice(drinkSearchDTO, pageable);
        List<Drink> drinks = drinksPage.getContent();

        List<DrinkWithPriceDTO> drinksDTO = new ArrayList<>();
        drinks.forEach(drink -> drinksDTO.add(new DrinkWithPriceDTO(drink)));

        HttpHeaders httpHeaderAttributes = ControllerUtils.createPageHeaderAttributes(drinksPage);
        return new ResponseEntity<>(drinksDTO, httpHeaderAttributes, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DrinkDTO> findOne(@PathVariable Long id) {
        Drink drink = drinkService.findOne(id);
        if (drink == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DrinkDTO(drink), HttpStatus.FOUND);
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasRole('BARMAN')")
    public ResponseEntity<String> saveDrink(@RequestBody DrinkDTO drinkDTO) {
        try {
            drinkService.saveDrink(drinkDTO);
            return new ResponseEntity<>("Drink successfully created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(consumes = "application/json")
    @PreAuthorize("hasRole('BARMAN')")
    public ResponseEntity<String> updateDrink(@RequestBody DrinkDTO drinkDTO) {

        try {
            Drink drink = drinkService.findOne(drinkDTO.getId());

            if (drink == null) {
                return new ResponseEntity<>("Drink cannot be null", HttpStatus.BAD_REQUEST);
            }
            drinkService.saveDrink(drinkDTO);
            return new ResponseEntity<>("Drink successfully updated", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteDrink(@PathVariable Long id) {

        Drink drink = drinkService.findOne(id);

        if (drink == null) {
            return new ResponseEntity<>("Drink does not exist with requested ID", HttpStatus.BAD_REQUEST);
        }
        drinkService.deleteDrink(drink);
        return new ResponseEntity<>("Drink successfully deleted", HttpStatus.OK);
    }

    @GetMapping(value = "/categories")
    @PreAuthorize("hasRole('WAITER')")
    public ResponseEntity<List<String>> findAllDrinkCategories() {
        List<String> categories = drinkService.findAllDrinkCategories();

        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
