package com.app.RestaurantApp.food;

import com.app.RestaurantApp.category.CategoryService;
import com.app.RestaurantApp.category.dto.CategoryDTO;
import com.app.RestaurantApp.enums.ItemType;
import com.app.RestaurantApp.food.dto.FoodDTO;
import com.app.RestaurantApp.item.ItemException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static com.app.RestaurantApp.food.Constants.*;

@SpringBootTest
public class FoodServiceIntegrationTests {

    @Autowired
    private FoodService foodService;

    @Test
    public void testFindOne() {
        // Test invoke
        Food food = foodService.findOne(FOOD_ID);

        // Verifying
        assertNotNull(food);
        assertEquals(FOUND_NAME, food.getName());
        assertEquals(FOUND_CATEGORY_NAME, food.getCategory().getName());
        assertEquals(FOUND_COST, food.getCost());
    }

    @Test
    public void testSaveFood() throws ItemException, FoodException {
        FoodDTO foodDTO = createFoodDTO(); // Creating test object

        // Test invoke
        Food food = foodService.saveFood(foodDTO);

        // Verifying
        assertNotNull(food);
        assertEquals(foodDTO.getName(), food.getName());
        assertEquals(foodDTO.getRecipe(), food.getRecipe());
        assertEquals(foodDTO.getCost(), food.getCost());
        assertEquals(foodDTO.getTimeToMake(), food.getTimeToMake());
        assertEquals(foodDTO.getItemType(), food.getItemType());
        assertEquals(foodDTO.getType(), food.getType().toString());
        assertEquals(foodDTO.getImage(), food.getImage());
        assertEquals(foodDTO.getDescription(), food.getDescription());
        assertFalse(food.isDeleted());
        assertEquals(foodDTO.getCategory().getName(), food.getCategory().getName());
    }

    @Test
    public void testDeleteFood(){
        Food food = foodService.findOne(FOOD_ID);

        // Test invoke
        foodService.deleteFood(food);

        // Verifying
        assertNull(foodService.findOne(FOOD_ID));
    }

    private FoodDTO createFoodDTO(){
        // This method creates testing object
        CategoryDTO category = new CategoryDTO(1L, "Supe");
        return new FoodDTO(1L, "Supa", 250.0, "Bas je slana", "putanja/supa", category, ItemType.FOOD, false, "Ma lako se pravi", 20, "APPETIZER");
    }

}
