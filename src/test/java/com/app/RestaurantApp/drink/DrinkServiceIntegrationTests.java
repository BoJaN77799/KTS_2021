package com.app.RestaurantApp.drink;

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

import static com.app.RestaurantApp.drink.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class DrinkServiceIntegrationTests {

    @Autowired
    private DrinkService drinkService;

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
    public void testSaveDrink() throws ItemException, DrinkException {
        DrinkDTO drinkDTO = createDrinkDTO(); // Creating test object

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
        assertEquals(drinkDTO.getCategory().getName(), drink.getCategory().getName());
    }

    @Test
    public void testDeleteDrink(){
        Drink food = drinkService.findOne(DELETE_DRINK_ID);

        // Test invoke
        drinkService.deleteDrink(food);

        // Verifying
        assertNull(drinkService.findOne(DELETE_DRINK_ID));
    }

    private DrinkDTO createDrinkDTO() {
        CategoryDTO category = new CategoryDTO(7L, "Alkoholna pica");
        return new DrinkDTO(7L, "Coca cola", 140.0, "Bas je gazirana", "putanja/cola", category, ItemType.DRINK, false, 0.5);
    }
}
