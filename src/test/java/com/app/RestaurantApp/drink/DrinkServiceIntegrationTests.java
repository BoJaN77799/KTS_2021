package com.app.RestaurantApp.drink;

import com.app.RestaurantApp.drinks.dto.DrinkCreateDTO;
import com.app.RestaurantApp.drinks.dto.DrinkSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.app.RestaurantApp.category.dto.CategoryDTO;
import com.app.RestaurantApp.drinks.Drink;
import com.app.RestaurantApp.drinks.DrinkException;
import com.app.RestaurantApp.drinks.DrinkService;
import com.app.RestaurantApp.drinks.dto.DrinkDTO;
import com.app.RestaurantApp.enums.ItemType;
import com.app.RestaurantApp.item.ItemException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.app.RestaurantApp.drink.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DrinkServiceIntegrationTests {

    @Autowired
    private DrinkService drinkService;

    @Test
    public void testGetDrinksWithPrice_WithName() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        DrinkSearchDTO drinkSearchDTO = new DrinkSearchDTO(NAME3, ALL, SPECIJALNI_MENI);

        Page<Drink> drinksPage = drinkService.getDrinksWithPrice(drinkSearchDTO, pageable);
        assertEquals(1, drinksPage.getContent().size());
    }

    @Test
    public void testGetDrinksWithPrice_WithCategory() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        DrinkSearchDTO drinkSearchDTO = new DrinkSearchDTO(ALL, CATEGORY1, LETNJI_MENI);

        Page<Drink> drinksPage = drinkService.getDrinksWithPrice(drinkSearchDTO, pageable);
        assertEquals(3, drinksPage.getContent().size());
    }

    @Test
    public void testGetDrinksWithPrice_WithBlanks() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        DrinkSearchDTO drinkSearchDTO = new DrinkSearchDTO("", "", "");

        Page<Drink> drinksPage = drinkService.getDrinksWithPrice(drinkSearchDTO, pageable);
        assertEquals(0, drinksPage.getContent().size());
    }

    @Test
    public void testGetDrinksWithPrice_WithBlanksOtherMenu() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        DrinkSearchDTO drinkSearchDTO = new DrinkSearchDTO("", "", LETNJI_MENI);

        Page<Drink> drinksPage = drinkService.getDrinksWithPrice(drinkSearchDTO, pageable);
        assertEquals(4, drinksPage.getContent().size());
    }

    @Test
    public void testGetDrinksWithPrice_WithNulls() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        DrinkSearchDTO drinkSearchDTO = new DrinkSearchDTO(null, null, null);

        Page<Drink> drinksPage = drinkService.getDrinksWithPrice(drinkSearchDTO, pageable);
        assertEquals(0, drinksPage.getContent().size());
    }

    @Test
    public void testGetDrinksWithPrice_WithNullsOtherMenu() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        DrinkSearchDTO drinkSearchDTO = new DrinkSearchDTO(null, null, LETNJI_MENI);

        Page<Drink> drinksPage = drinkService.getDrinksWithPrice(drinkSearchDTO, pageable);
        assertEquals(4, drinksPage.getContent().size());
    }

    @Test
    public void testFindOne(){
        // Test invoke
        Drink drink = drinkService.findOne(DRINK_ID);

        // Verifying
        assertNotNull(drink);
        assertEquals(FOUND_NAME, drink.getName());
        assertEquals(FOUND_CATEGORY_NAME, drink.getCategory().getName());
        assertEquals(FOUND_COST, drink.getCost());
    }

    @Test
    @Transactional
    public void testSaveDrink() throws ItemException, DrinkException {
        DrinkCreateDTO drinkDTO = createDrinkDTO(); // Creating test object

        // Test invoke
        Drink drink = drinkService.saveDrink(drinkDTO);

        // Verifying
        assertNotNull(drink);
        assertEquals(drinkDTO.getName(), drink.getName());
        assertEquals(drinkDTO.getCost(), drink.getCost());
        assertEquals(drinkDTO.getItemType(), drink.getItemType());
        assertEquals(drinkDTO.getImage(), drink.getImage());
        assertEquals(drinkDTO.getDescription(), drink.getDescription());
        assertFalse(drink.isDeleted());
        assertEquals(drinkDTO.getCategory(), drink.getCategory().getName());
    }

    @Test
    @Transactional
    public void testDeleteDrink(){
        Drink drink = drinkService.findOne(DELETE_DRINK_ID);

        // Test invoke
        drinkService.deleteDrink(drink);

        // Verifying
        assertNull(drinkService.findOne(DELETE_DRINK_ID));
    }

    @Test
    public void testFindAllDrinks(){
        // Test invoke
        List<Drink> drinks = drinkService.findAll();

        // Verifying
        assertNotNull(drinks);
        assertTrue(drinks.size() >= 3); // condition is independent of test order
    }

    @Test
    public void testfindAllDrinkCategories() {
        List<String> categories = drinkService.findAllDrinkCategories();

        assertEquals(categories.size(), 2);
    }

    private DrinkCreateDTO createDrinkDTO() {
        CategoryDTO category = new CategoryDTO(7L, "Alkoholna pica");
        return new DrinkCreateDTO(70L, "Coca Cola", 140.0, "Bas je gazirana", "putanja/cola", category, ItemType.DRINK, false, 0.5);
    }
}
