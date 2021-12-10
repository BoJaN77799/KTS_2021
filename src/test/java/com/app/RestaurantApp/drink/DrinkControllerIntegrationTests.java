package com.app.RestaurantApp.drink;

import com.app.RestaurantApp.category.dto.CategoryDTO;
import com.app.RestaurantApp.drinks.Drink;
import com.app.RestaurantApp.drinks.DrinkService;
import com.app.RestaurantApp.drinks.dto.DrinkDTO;
import com.app.RestaurantApp.enums.ItemType;
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
import static org.junit.jupiter.api.Assertions.*;
import static com.app.RestaurantApp.drink.Constants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DrinkControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DrinkService drinkService;

    private final HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    void setup(){
        ResponseEntity<UserTokenState> responseEntity =
                restTemplate.postForEntity("/api/users/login",
                        new JwtAuthenticationRequest(BARMAN_EMAIL, BARMAN_PWD),
                        UserTokenState.class);

        String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();
        headers.add("Authorization", "Bearer " + accessToken);
    }

    @Test
    public void testSaveDrink(){

        HttpEntity<DrinkDTO> httpEntity = new HttpEntity<>(createDrinkDTO(), headers);

        List<Drink> drinks =  drinkService.findAll(); // before test

        ResponseEntity<String> entity = restTemplate
                .exchange("/api/drinks", HttpMethod.POST, httpEntity, String.class);

        assertEquals(HttpStatus.CREATED, entity.getStatusCode());

        String message = entity.getBody();

        assertNotNull(message);
        assertEquals("Drink successfully created", message);
        assertEquals(drinks.size() + 1, drinkService.findAll().size());
    }

    @Test
    public void testUpdateDrink(){

        DrinkDTO modifiedDrinkDTO = createDrinkDTO();
        modifiedDrinkDTO.setId(DRINK_ID);
        modifiedDrinkDTO.setVolume(UPDATE_VOLUME);

        HttpEntity<DrinkDTO> httpEntity = new HttpEntity<>(modifiedDrinkDTO, headers);

        ResponseEntity<String> entity = restTemplate
                .exchange("/api/drinks", HttpMethod.PUT, httpEntity, String.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        String message = entity.getBody();

        assertNotNull(message);
        assertEquals("Drink successfully updated", message);
        Drink drink = drinkService.findOne(DRINK_ID);
        assertEquals(DRINK_ID, drink.getId());
        assertEquals(UPDATE_VOLUME, drink.getVolume());
    }

    @Test
    public void testDeleteDrink(){
        HttpEntity<DrinkDTO> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String> entity = restTemplate
                .exchange("/api/drinks/{id}", HttpMethod.DELETE, httpEntity, String.class, DELETE_DRINK_ID_CONTROLLER);

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        String message = entity.getBody();

        assertNotNull(message);
        assertEquals("Drink successfully deleted", message);
        assertNull(drinkService.findOne(DELETE_DRINK_ID_CONTROLLER));
    }

    @Test
    public void testDeleteDrink_BadRequest(){
        HttpEntity<DrinkDTO> httpEntity = new HttpEntity<>(headers);

        List<Drink> drinks = drinkService.findAll();

        ResponseEntity<String> entity = restTemplate
                .exchange("/api/drinks/{id}", HttpMethod.DELETE, httpEntity, String.class, DELETE_DRINK_ID_BAD_REQUEST);

        assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());

        String message = entity.getBody();

        assertNotNull(message);
        assertEquals("Drink does not exist with requested ID", message);
        assertEquals(drinks.size(), drinkService.findAll().size()); // nothing changed
    }

    private DrinkDTO createDrinkDTO() {
        CategoryDTO category = new CategoryDTO(7L, "Alkoholna pica");
        return new DrinkDTO(null, "Coca cola", 140.0, "Bas je gazirana", "putanja/cola", category, ItemType.DRINK, false, 0.5);
    }
    
}
