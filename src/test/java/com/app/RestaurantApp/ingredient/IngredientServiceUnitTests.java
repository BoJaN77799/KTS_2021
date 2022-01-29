package com.app.RestaurantApp.ingredient;

import com.app.RestaurantApp.category.Category;
import com.app.RestaurantApp.category.CategoryService;
import com.app.RestaurantApp.enums.FoodType;
import com.app.RestaurantApp.enums.ItemType;
import com.app.RestaurantApp.food.Food;
import com.app.RestaurantApp.food.FoodRepository;
import com.app.RestaurantApp.food.FoodService;
import com.app.RestaurantApp.food.dto.FoodDTO;
import com.app.RestaurantApp.food.dto.FoodWithIngredientsDTO;
import com.app.RestaurantApp.ingredient.dto.IngredientDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashSet;
import java.util.List;

import static com.app.RestaurantApp.food.Constants.CATEGORY_NAME;
import static com.app.RestaurantApp.ingredient.Constants.*;
import static com.app.RestaurantApp.ingredient.Constants.FOOD_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
public class IngredientServiceUnitTests {

    @Autowired
    private IngredientService ingredientService;

    @MockBean
    private FoodService foodServiceMock;

    @Test
    public void testSaveIngredientsToFood() {
        FoodWithIngredientsDTO food = createFoodWithIngredients();
        Food foodMocked = new Food(); foodMocked.setId(FOOD_ID);
        given(foodServiceMock.saveFood(any(Food.class))).willReturn(foodMocked);
        given(foodServiceMock.findOne(FOOD_ID)).willReturn(foodMocked);

        // Test invoke
        Food savedFood = ingredientService.saveIngredientsToFood(food);

        // Verifying
        verify(foodServiceMock, times(1)).saveFood(any(Food.class));
        assertNotNull(food);
        assertEquals(FOOD_ID, savedFood.getId());
        assertEquals(3 , savedFood.getIngredients().size());
        assertNotNull(savedFood.getIngredients().stream().filter(ingredient -> ingredient.getName().equals(INGREDIENT_NAME_1)).findFirst());
        assertNotNull(savedFood.getIngredients().stream().filter(ingredient -> ingredient.getName().equals(INGREDIENT_NAME_2)).findFirst());
        assertNotNull(savedFood.getIngredients().stream().filter(ingredient -> ingredient.getName().equals(INGREDIENT_NAME_3)).findFirst());
    }

    private FoodWithIngredientsDTO createFoodWithIngredients() {
        HashSet<IngredientDTO> ingredients = new HashSet<>(List.of(
                new IngredientDTO(15L, INGREDIENT_NAME_1, false),
                new IngredientDTO(1L, INGREDIENT_NAME_2, false),
                new IngredientDTO(14L, INGREDIENT_NAME_3, false)));
        return new FoodWithIngredientsDTO(FOOD_ID, ingredients);
    }
}