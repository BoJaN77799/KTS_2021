package com.app.RestaurantApp.food;

import com.app.RestaurantApp.food.dto.FoodSearchDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import java.util.ArrayList;
import java.util.List;

import static com.app.RestaurantApp.food.Constants.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FoodServiceUnitTests {

    @Autowired
    private FoodService foodService;

    @MockBean
    private FoodRepository foodRepository;

    @BeforeEach
    public void setup() {
        Pageable pageableSetup = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        List<Food> foodsSetup = createFoods();
        Page<Food> foodsPageSetup = new PageImpl<>(foodsSetup, pageableSetup, PAGEABLE_TOTAL_ELEMENTS);

        given(foodRepository.findAllWithPriceByCriteria(NAME2, ALL, ALL, pageableSetup)).willReturn(foodsPageSetup);
        given(foodRepository.findAllWithPriceByCriteria(ALL, CATEGORY1, ALL, pageableSetup)).willReturn(foodsPageSetup);
        given(foodRepository.findAllWithPriceByCriteria(ALL, ALL, MAIN_DISH, pageableSetup)).willReturn(foodsPageSetup);
        given(foodRepository.findAllWithPriceByCriteria(ALL, ALL, ALL, pageableSetup)).willReturn(foodsPageSetup);
    }

    @Test
    public void testGetFoodWithPriceWithName() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO(NAME2, ALL, ALL);
        Page<Food> foodsPage = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        verify(foodRepository, times(1)).findAllWithPriceByCriteria(NAME2, ALL, ALL, pageable);
        assertEquals(2, foodsPage.getNumberOfElements());
    }

    @Test
    public void testGetFoodWithPriceWithCategory() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO(ALL, CATEGORY1, ALL);
        Page<Food> foodsPage = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        verify(foodRepository, times(1)).findAllWithPriceByCriteria(ALL, CATEGORY1, ALL, pageable);
        assertEquals(2, foodsPage.getNumberOfElements());
    }

    @Test
    public void testGetFoodWithPriceWithType() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO(ALL, ALL, TYPE1);
        Page<Food> foodsPage = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        verify(foodRepository, times(1)).findAllWithPriceByCriteria(ALL, ALL, TYPE1, pageable);
        assertEquals(2, foodsPage.getNumberOfElements());
    }

    @Test
    public void testGetFoodWithPriceWithNulls() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO(null, null, null);
        Page<Food> foodsPage = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        verify(foodRepository, times(1)).findAllWithPriceByCriteria(ALL, ALL, ALL, pageable);
        assertEquals(2, foodsPage.getNumberOfElements());
    }

    @Test
    public void testGetFoodWithPriceWithBlanks() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO("", "", "");
        Page<Food> foodsPage = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        verify(foodRepository, times(1)).findAllWithPriceByCriteria(ALL, ALL, ALL, pageable);
        assertEquals(2, foodsPage.getNumberOfElements());
    }



    private List<Food> createFoods() {
        List<Food> foods = new ArrayList<>();

        Food food1 = new Food();
        food1.setId(1L);
        food1.setName("Jagnjece pecnje");
        foods.add(food1);

        Food food2 = new Food();
        food2.setId(2L);
        food2.setName("Jagnjece snicle");
        foods.add(food2);

        return foods;
    }
}
