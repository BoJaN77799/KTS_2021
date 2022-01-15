package com.app.RestaurantApp.ingredient;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
}