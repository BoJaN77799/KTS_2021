package com.app.RestaurantApp.food;

import com.app.RestaurantApp.food.dto.FoodSearchDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static com.app.RestaurantApp.food.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FoodServiceIntegrationTests {

    @Autowired
    private FoodService foodService;

    @Test
    public void testGetFoodWithPrice_WithName() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO(NAME2, ALL, ALL);

        Page<Food> food = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        assertEquals(1, food.getContent().size());
        assertEquals(4L, food.getContent().get(0).getId());
    }

    @Test
    public void testFetFoodWithPrice_WithCategory() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO(ALL, CATEGORY1, ALL);

        Page<Food> food = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        assertEquals(2, food.getContent().size());
        assertEquals(3L, food.getContent().get(0).getId());
    }

    @Test
    public void testFetFoodWithPrice_WithType() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO(ALL, ALL, TYPE1);

        Page<Food> food = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        assertEquals(2, food.getContent().size());
        assertEquals(4L, food.getContent().get(0).getId());
    }

    @Test
    public void testFetFoodWithPrice_WithNameAndCategory() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO(NAME1, CATEGORY1, ALL);

        Page<Food> food = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        assertEquals(0, food.getContent().size());
    }

    @Test
    public void testFetFoodWithPrice_All() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO("", "", "");

        Page<Food> food = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        assertEquals(5, food.getContent().size());
    }

}
