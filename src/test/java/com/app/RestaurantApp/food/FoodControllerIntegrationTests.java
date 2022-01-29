package com.app.RestaurantApp.food;

import com.app.RestaurantApp.category.dto.CategoryDTO;
import com.app.RestaurantApp.enums.ItemType;
import com.app.RestaurantApp.food.dto.FoodDTO;
import com.app.RestaurantApp.food.dto.FoodWithPriceDTO;
import com.app.RestaurantApp.security.auth.JwtAuthenticationRequest;
import com.app.RestaurantApp.users.FileUploadUtilTest;
import com.app.RestaurantApp.users.appUser.AppUser;
import com.app.RestaurantApp.users.dto.UserTokenState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.app.RestaurantApp.food.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FoodControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FoodService foodService;

    @Autowired
    private MockMvc mockMvc;

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
    public void testSaveFood() throws Exception {
        List<Food> food =  foodService.findAll(); // before test

        MockMultipartFile image = new MockMultipartFile("multipartImageFile", "default_food.jpg",
                MediaType.IMAGE_JPEG_VALUE, FileUploadUtilTest.getTestFileInputStream());

        mockMvc.perform(multipart("/api/food")
                        .file(image)
                        .headers(headers)
                        .param("name", "Rumenko")
                        .param("cost", "200")
                        .param("description", "Mnogo je dobar")
                        .param("image", "food_photos/default_food.jpg")
                        .param("category", "Sladoled")
                        .param("itemType", "FOOD")
                        .param("deleted", "false")
                        .param("recipe", "Lako se pravi")
                        .param("timeToMake", "20")
                        .param("type", "DESERT")
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value("Food successfully created"));

        assertEquals(food.size() + 1, foodService.findAll().size());

        //cleanup
        Food savedFood = foodService.findOne(14L);
        if (savedFood != null){
            String foodImage = savedFood.getImage();
            Path resultPath = Paths.get(foodImage);
            assertTrue(resultPath.toFile().exists() && !resultPath.toFile().isDirectory());
            assertTrue(resultPath.toFile().delete());
        }
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

    @Test
    public void testGetFoodWithPrice_All() {
        logInAsWaiter();
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<FoodWithPriceDTO[]> responseEntity = restTemplate
                .exchange("/api/food?name=&category=&type=&page=0&size=5", HttpMethod.GET, httpEntity, FoodWithPriceDTO[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(4, Objects.requireNonNull(responseEntity.getBody()).length);
        assertEquals(2L, responseEntity.getBody()[0].getId());
    }

    @Test
    public void testGetFoodWithPrice_WithName() {
        logInAsWaiter();
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<FoodWithPriceDTO[]> responseEntity = restTemplate
                .exchange("/api/food?name=jagnjece pecenje&category=&type=&page=0&size=5", HttpMethod.GET, httpEntity, FoodWithPriceDTO[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, Objects.requireNonNull(responseEntity.getBody()).length);
        assertEquals(4L, responseEntity.getBody()[0].getId());
    }

    @Test
    public void testGetFoodWithPrice_WithNameAndType() {
        logInAsWaiter();
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<FoodWithPriceDTO[]> responseEntity = restTemplate
                .exchange("/api/food?name=jagnjece pecenje&category=&type=APPETIZER&page=0&size=5", HttpMethod.GET, httpEntity, FoodWithPriceDTO[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(0, Objects.requireNonNull(responseEntity.getBody()).length);
    }

    @Test
    public void testGetFoodWithPrice_WithCategory() {
        logInAsWaiter();
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<FoodWithPriceDTO[]> responseEntity = restTemplate
                .exchange("/api/food?name=&category=Mlecni proizvodi&type=&page=0&size=5", HttpMethod.GET, httpEntity, FoodWithPriceDTO[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, Objects.requireNonNull(responseEntity.getBody()).length);
        assertEquals(2L, responseEntity.getBody()[0].getId());
    }

    @Test
    public void testGetFoodWithPrice_WithCategoryOtherMenu() {
        logInAsWaiter();
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<FoodWithPriceDTO[]> responseEntity = restTemplate
                .exchange("/api/food?name=&category=sladoledi&type=&page=0&size=5&menu=Specijalni meni", HttpMethod.GET, httpEntity, FoodWithPriceDTO[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, Objects.requireNonNull(responseEntity.getBody()).length);
        assertEquals(6L, responseEntity.getBody()[0].getId());
    }

    @Test
    public void testGetFoodWithPrice_WithCategoryAndType() {
        logInAsWaiter();
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<FoodWithPriceDTO[]> responseEntity = restTemplate
                .exchange("/api/food?name=&category=jagnjece meso&type=MAIN_DISH&page=0&size=5", HttpMethod.GET, httpEntity, FoodWithPriceDTO[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, Objects.requireNonNull(responseEntity.getBody()).length);
    }

    @Test
    public void testFindAllFoodCategories() {
        logInAsWaiter();
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String[]> responseEntity = restTemplate
                .exchange("/api/food/categories", HttpMethod.GET, httpEntity, String[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(6, Objects.requireNonNull(responseEntity.getBody()).length);
    }

    private void logInAsWaiter() {
        ResponseEntity<UserTokenState> responseEntity =
                restTemplate.postForEntity("/api/users/login",
                        new JwtAuthenticationRequest(WAITER_EMAIL, WAITER_PWD),
                        UserTokenState.class);

        String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();

        headers.set("Authorization", "Bearer " + accessToken);
    }

    private FoodDTO createFoodDTO(){
        CategoryDTO category = new CategoryDTO(1L, "Supe");
        return new FoodDTO(null, "Supa", 250.0, "Bas je slana", "putanja/supa", category, ItemType.FOOD, false, "Ma lako se pravi", 20, "APPETIZER");
    }
}
