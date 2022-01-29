package com.app.RestaurantApp.ingredient;

import com.app.RestaurantApp.category.dto.CategoryDTO;
import com.app.RestaurantApp.enums.ItemType;
import com.app.RestaurantApp.food.Food;
import com.app.RestaurantApp.food.FoodService;
import com.app.RestaurantApp.food.dto.FoodWithIngredientsDTO;
import com.app.RestaurantApp.ingredient.dto.IngredientDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static com.app.RestaurantApp.ingredient.Constants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IngredientServiceIntegrationTests {

    @Autowired
    private IngredientService ingredientService;

    @Test
    public void testGetAll() {
        // Test invoke
        List<Ingredient> ingredients = ingredientService.getAll();

        // Verifying
        assertNotNull(ingredients);
        assertEquals(FOUND_SIZE, ingredients.size());
    }

    @Test
    public void testSaveIngredientsToFood() {
        // prepare data
        FoodWithIngredientsDTO foodWithIngredientsDTO = createFoodWithIngredients();

        // test invoke
        Food food = ingredientService.saveIngredientsToFood(foodWithIngredientsDTO);

        // verifying
        assertEquals(FOOD_ID, food.getId());
        assertEquals(3 , food.getIngredients().size());
        assertNotNull(food.getIngredients().stream().filter(ingredient -> ingredient.getName().equals(INGREDIENT_NAME_1)).findFirst());
        assertNotNull(food.getIngredients().stream().filter(ingredient -> ingredient.getName().equals(INGREDIENT_NAME_2)).findFirst());
        assertNotNull(food.getIngredients().stream().filter(ingredient -> ingredient.getName().equals(INGREDIENT_NAME_3)).findFirst());
    }

    private FoodWithIngredientsDTO createFoodWithIngredients() {
        HashSet<IngredientDTO> ingredients = new HashSet<>(List.of(
                new IngredientDTO(15L, INGREDIENT_NAME_1, false),
                new IngredientDTO(1L, INGREDIENT_NAME_2, false),
                new IngredientDTO(14L, INGREDIENT_NAME_3, false)));
        return new FoodWithIngredientsDTO(FOOD_ID, ingredients);
    }
}