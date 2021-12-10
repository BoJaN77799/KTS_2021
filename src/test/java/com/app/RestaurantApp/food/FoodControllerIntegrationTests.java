package com.app.RestaurantApp.food;

import com.app.RestaurantApp.category.dto.CategoryDTO;
import com.app.RestaurantApp.enums.ItemType;
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

import static com.app.RestaurantApp.food.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FoodControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FoodService foodService;

    private final HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    void setup(){
        ResponseEntity<UserTokenState> responseEntity =
                restTemplate.postForEntity("/api/users/login",
                        new JwtAuthenticationRequest(COOK_EMAIL, COOK_PWD),
                        UserTokenState.class);

        String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();
        headers.add("Authorization", "Bearer " + accessToken);
    }

    @Test
    public void testSaveFood(){
        HttpEntity<FoodDTO> httpEntity = new HttpEntity<>(createFoodDTO(), headers);

        List<Food> food =  foodService.findAll(); // before test

        ResponseEntity<String> entity = restTemplate
                .exchange("/api/food", HttpMethod.POST, httpEntity, String.class);

        assertEquals(HttpStatus.CREATED, entity.getStatusCode());

        String message = entity.getBody();

        assertNotNull(message);
        assertEquals("Food successfully created", message);
        assertEquals(food.size() + 1, foodService.findAll().size());
    }

    @Test
    public void testUpdateFood(){

        FoodDTO modifiedFoodDTO = createFoodDTO();
        modifiedFoodDTO.setId(FOOD_ID);
        modifiedFoodDTO.setTimeToMake(UPDATE_TIME_TO_MAKE);

        HttpEntity<FoodDTO> httpEntity = new HttpEntity<>(modifiedFoodDTO, headers);

        ResponseEntity<String> entity = restTemplate
                .exchange("/api/food", HttpMethod.PUT, httpEntity, String.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        String message = entity.getBody();

        assertNotNull(message);
        assertEquals("Food successfully updated", message);
        Food food = foodService.findOne(FOOD_ID);
        assertEquals(FOOD_ID, food.getId());
        assertEquals(UPDATE_TIME_TO_MAKE, food.getTimeToMake());
    }

    @Test
    public void testDeleteFood(){
        HttpEntity<FoodDTO> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> entity = restTemplate
                .exchange("/api/food/{id}", HttpMethod.DELETE, httpEntity, String.class, DELETE_FOOD_ID_CONTROLLER);

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        String message = entity.getBody();

        assertNotNull(message);
        assertEquals("Food successfully deleted", message);
        assertNull(foodService.findOne(DELETE_FOOD_ID_CONTROLLER));
    }

    @Test
    public void testDeleteFood_BadRequest(){
        HttpEntity<FoodDTO> httpEntity = new HttpEntity<>(headers);

        List<Food> foods = foodService.findAll();

        ResponseEntity<String> entity = restTemplate
                .exchange("/api/food/{id}", HttpMethod.DELETE, httpEntity, String.class, DELETE_FOOD_ID_BAD_REQUEST);

        assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());

        String message = entity.getBody();

        assertNotNull(message);
        assertEquals("Food does not exist with requested ID", message);
        assertEquals(foods.size(), foodService.findAll().size()); // nothing changed
    }

    private FoodDTO createFoodDTO(){
        // This method creates testing object
        CategoryDTO category = new CategoryDTO(1L, "Supe");
        return new FoodDTO(null, "Supa", 250.0, "Bas je slana", "putanja/supa", category, ItemType.FOOD, false, "Ma lako se pravi", 20, "APPETIZER");
    }
}