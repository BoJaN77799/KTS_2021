package com.app.RestaurantApp.drink;

import com.app.RestaurantApp.drinks.Drink;
import com.app.RestaurantApp.drinks.DrinkService;
import com.app.RestaurantApp.drinks.dto.DrinkSearchDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static com.app.RestaurantApp.drink.Constants.*;
import static com.app.RestaurantApp.drink.Constants.ALL;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DrinkServiceIntegrationTests {

    @Autowired
    private DrinkService drinkService;

    @Test
    public void testGetDrinksWithPrice_WithName() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        DrinkSearchDTO drinkSearchDTO = new DrinkSearchDTO(NAME3, ALL);

        Page<Drink> drinksPage = drinkService.getDrinksWithPrice(drinkSearchDTO, pageable);
        assertEquals(1, drinksPage.getContent().size());
    }

    @Test
    public void testGetDrinksWithPrice_WithCategory() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        DrinkSearchDTO drinkSearchDTO = new DrinkSearchDTO(ALL, CATEGORY1);

        Page<Drink> drinksPage = drinkService.getDrinksWithPrice(drinkSearchDTO, pageable);
        assertEquals(3, drinksPage.getContent().size());
    }

    @Test
    public void testGetDrinksWithPrice_WithBlanks() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        DrinkSearchDTO drinkSearchDTO = new DrinkSearchDTO("", "");

        Page<Drink> drinksPage = drinkService.getDrinksWithPrice(drinkSearchDTO, pageable);
        assertEquals(4, drinksPage.getContent().size());
    }

    @Test
    public void testGetDrinksWithPrice_WithNulls() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        DrinkSearchDTO drinkSearchDTO = new DrinkSearchDTO(null, null);

        Page<Drink> drinksPage = drinkService.getDrinksWithPrice(drinkSearchDTO, pageable);
        assertEquals(4, drinksPage.getContent().size());
    }
}
