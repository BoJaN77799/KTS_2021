package com.app.RestaurantApp.food;

import com.app.RestaurantApp.food.dto.FoodDTO;
import com.app.RestaurantApp.food.dto.FoodSearchDTO;
import com.app.RestaurantApp.food.dto.FoodWithIngredientsDTO;
import com.app.RestaurantApp.ingredient.dto.IngredientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.app.RestaurantApp.category.dto.CategoryDTO;
import com.app.RestaurantApp.enums.ItemType;
import com.app.RestaurantApp.item.ItemException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static com.app.RestaurantApp.food.Constants.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FoodServiceIntegrationTests {

    @Autowired
    private FoodService foodService;

    @Test
    public void testGetFoodWithPrice_WithName() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO(NAME2, ALL, ALL, STALNI_MENI);

        Page<Food> food = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        assertEquals(1, food.getContent().size());
        assertEquals(4L, food.getContent().get(0).getId());
    }

    @Test
    public void testGetFoodWithPrice_WithNameOtherMenu() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO(NAME2, ALL, ALL, SPECIJALNI_MENI);

        Page<Food> food = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        assertEquals(0, food.getContent().size());
    }

    @Test
    public void testFetFoodWithPrice_WithCategory() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO(ALL, CATEGORY1, ALL, STALNI_MENI);

        Page<Food> food = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        assertEquals(2, food.getContent().size());
        assertEquals(3L, food.getContent().get(0).getId());
    }

    @Test
    public void testFetFoodWithPrice_WithType() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO(ALL, ALL, TYPE1, STALNI_MENI);

        Page<Food> food = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        assertEquals(2, food.getContent().size());
        assertEquals(4L, food.getContent().get(0).getId());
    }

    @Test
    public void testFetFoodWithPrice_WithNameAndCategory() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO(NAME1, CATEGORY1, ALL, STALNI_MENI);

        Page<Food> food = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        assertEquals(0, food.getContent().size());
    }

    @Test
    public void testFetFoodWithPrice_All() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO("", "", "", "");

        Page<Food> food = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        assertEquals(5, food.getContent().size());
    }

    @Test
    public void testFetFoodWithPrice_OtherMenuAll() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO("", "", "", SPECIJALNI_MENI);

        Page<Food> food = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        assertEquals(1, food.getContent().size());
    }
    
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
    @Transactional
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
        assertEquals(foodDTO.getCategory(), food.getCategory().getName());
    }

    @Test
    @Transactional
    public void testDeleteFood(){
        Food food = foodService.findOne(DELETE_FOOD_ID);

        // Test invoke
        foodService.deleteFood(food);

        // Verifying
        assertNull(foodService.findOne(DELETE_FOOD_ID));
    }

    @Test
    public void testFindAllFood(){
        // Test invoke
        List<Food> food = foodService.findAll();

        // Verifying
        assertNotNull(food);
        assertTrue(food.size() >= 5); // condition is independent of test order
    }

    @Test
    public void testfindAllFoodCategories() {
        List<String> categories = foodService.findAllFoodCategories();

        assertEquals(categories.size(), 5);
    }

    private FoodDTO createFoodDTO(){
        // This method creates testing object
        CategoryDTO category = new CategoryDTO(1L, "Supe");
        return new FoodDTO(1L, "Supa", 250.0, "Bas je slana", "putanja/supa", category, ItemType.FOOD, false, "Ma lako se pravi", 20, "APPETIZER");
    }

}
