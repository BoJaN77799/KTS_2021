package com.app.RestaurantApp.ingredient;

import com.app.RestaurantApp.food.Food;
import com.app.RestaurantApp.food.dto.FoodDTO;
import com.app.RestaurantApp.security.auth.JwtAuthenticationRequest;
import com.app.RestaurantApp.users.dto.UserTokenState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

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
}
