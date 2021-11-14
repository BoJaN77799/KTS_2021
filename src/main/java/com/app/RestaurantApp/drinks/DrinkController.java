package com.app.RestaurantApp.drinks;


import com.app.RestaurantApp.drinks.dto.DrinkSearchDTO;
import com.app.RestaurantApp.drinks.dto.DrinkWithPriceDTO;
import org.springframework.data.domain.Pageable;
import com.app.RestaurantApp.drinks.dto.DrinkDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping(value = "/{id}")
    public ResponseEntity<DrinkDTO> findOne(@PathVariable Long id) {
        Drink drink = drinkService.findOne(id);
        if (drink == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new DrinkDTO(drink), HttpStatus.CREATED);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<DrinkDTO> saveDrink(@RequestBody DrinkDTO drinkDTO) {
        drinkDTO.setDeleted(false);
        Drink drink = drinkService.saveDrink(drinkDTO);
        return new ResponseEntity<>(new DrinkDTO(drink), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<DrinkDTO> updateDrink(@RequestBody DrinkDTO drinkDTO) {

        Drink drink = drinkService.findOne(drinkDTO.getId());

        if (drink == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        drink = drinkService.saveDrink(drinkDTO);
        return new ResponseEntity<>(new DrinkDTO(drink), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteDrink(@PathVariable Long id) {

        Drink drink = drinkService.findOne(id);

        if (drink == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        drinkService.deleteDrink(drink);
        return new ResponseEntity<>("Drink successfully deleted.", HttpStatus.OK);
    }
}
