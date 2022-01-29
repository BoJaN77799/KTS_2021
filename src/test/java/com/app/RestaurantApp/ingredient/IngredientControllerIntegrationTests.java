package com.app.RestaurantApp.ingredient;

import com.app.RestaurantApp.food.Food;
import com.app.RestaurantApp.food.dto.FoodDTO;
import com.app.RestaurantApp.food.dto.FoodWithIngredientsDTO;
import com.app.RestaurantApp.ingredient.dto.IngredientDTO;
import com.app.RestaurantApp.security.auth.JwtAuthenticationRequest;
import com.app.RestaurantApp.users.dto.UserTokenState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import static com.app.RestaurantApp.ingredient.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IngredientControllerIntegrationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IngredientService ingredientService;

    private final HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    void setup(){
        ResponseEntity<UserTokenState> responseEntity =
                restTemplate.postForEntity("/api/users/login",
                        new JwtAuthenticationRequest(HEAD_COOK_EMAIL, HEAD_COOK_PWD),
                        UserTokenState.class);

        String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();
        headers.add("Authorization", "Bearer " + accessToken);
    }

    @Test
    public void testFindAll(){
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        List<Ingredient> ingredients =  ingredientService.getAll(); // before test

        ResponseEntity<Ingredient[]> entity = restTemplate
                .exchange("/api/ingredients/all", HttpMethod.GET, httpEntity, Ingredient[].class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        List<Ingredient> ingredientList = List.of(Objects.requireNonNull(entity.getBody()));

        assertNotNull(ingredientList);
        assertEquals(ingredients.size(), ingredientList.size());
    }

    @Test
    public void saveIngredients(){
        FoodWithIngredientsDTO food = createFoodWithIngredients();
        HttpEntity<FoodWithIngredientsDTO> httpEntity = new HttpEntity<>(food, headers);

        ResponseEntity<String> entity = restTemplate
                .exchange("/api/ingredients", HttpMethod.POST, httpEntity, String.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        String message = entity.getBody();

        assertNotNull(message);
        assertEquals(INGREDIENT_SUCCESS_SAVE, message);
    }

    private FoodWithIngredientsDTO createFoodWithIngredients() {
        HashSet<IngredientDTO> ingredients = new HashSet<>(List.of(
                new IngredientDTO(15L, INGREDIENT_NAME_1, false),
                new IngredientDTO(1L, INGREDIENT_NAME_2, false),
                new IngredientDTO(14L, INGREDIENT_NAME_3, false)));
        return new FoodWithIngredientsDTO(FOOD_ID, ingredients);
    }
}
