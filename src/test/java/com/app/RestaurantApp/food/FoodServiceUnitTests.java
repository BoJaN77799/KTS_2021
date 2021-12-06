package com.app.RestaurantApp.food;

import com.app.RestaurantApp.category.Category;
import com.app.RestaurantApp.category.CategoryService;
import com.app.RestaurantApp.category.dto.CategoryDTO;
import com.app.RestaurantApp.enums.ItemType;
import com.app.RestaurantApp.food.dto.FoodDTO;
import com.app.RestaurantApp.food.dto.FoodSearchDTO;
import com.app.RestaurantApp.item.ItemException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import java.util.ArrayList;
import java.util.List;

import static com.app.RestaurantApp.food.Constants.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FoodServiceUnitTests {

    @Autowired
    private FoodService foodService;

    @MockBean
    private CategoryService categoryServiceMock;

    @MockBean
    private FoodRepository foodRepositoryMock;

    @BeforeEach
    public void setup() {
        Pageable pageableSetup = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        List<Food> foodsSetup = createFoods();
        Page<Food> foodsPageSetup = new PageImpl<>(foodsSetup, pageableSetup, PAGEABLE_TOTAL_ELEMENTS);

        given(foodRepositoryMock.findAllWithPriceByCriteria(NAME2, ALL, ALL, pageableSetup)).willReturn(foodsPageSetup);
        given(foodRepositoryMock.findAllWithPriceByCriteria(ALL, CATEGORY1, ALL, pageableSetup)).willReturn(foodsPageSetup);
        given(foodRepositoryMock.findAllWithPriceByCriteria(ALL, ALL, MAIN_DISH, pageableSetup)).willReturn(foodsPageSetup);
        given(foodRepositoryMock.findAllWithPriceByCriteria(ALL, ALL, ALL, pageableSetup)).willReturn(foodsPageSetup);

        Category category = new Category(1L, "Supe");
        given(categoryServiceMock.findOne(category.getId())).willReturn(category);
        given(categoryServiceMock.insertCategory(new CategoryDTO(category))).willReturn(category);
    }

    @Test
    public void testGetFoodWithPriceWithName() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO(NAME2, ALL, ALL);
        Page<Food> foodsPage = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        verify(foodRepositoryMock, times(1)).findAllWithPriceByCriteria(NAME2, ALL, ALL, pageable);
        assertEquals(2, foodsPage.getNumberOfElements());
    }

    @Test
    public void testGetFoodWithPriceWithCategory() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO(ALL, CATEGORY1, ALL);
        Page<Food> foodsPage = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        verify(foodRepositoryMock, times(1)).findAllWithPriceByCriteria(ALL, CATEGORY1, ALL, pageable);
        assertEquals(2, foodsPage.getNumberOfElements());
    }

    @Test
    public void testGetFoodWithPriceWithType() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO(ALL, ALL, TYPE1);
        Page<Food> foodsPage = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        verify(foodRepositoryMock, times(1)).findAllWithPriceByCriteria(ALL, ALL, TYPE1, pageable);
        assertEquals(2, foodsPage.getNumberOfElements());
    }

    @Test
    public void testGetFoodWithPriceWithNulls() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO(null, null, null);
        Page<Food> foodsPage = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        verify(foodRepositoryMock, times(1)).findAllWithPriceByCriteria(ALL, ALL, ALL, pageable);
        assertEquals(2, foodsPage.getNumberOfElements());
    }

    @Test
    public void testGetFoodWithPriceWithBlanks() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO("", "", "");
        Page<Food> foodsPage = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        verify(foodRepositoryMock, times(1)).findAllWithPriceByCriteria(ALL, ALL, ALL, pageable);
        assertEquals(2, foodsPage.getNumberOfElements());
    }

    @Test
    public void testSaveFood() throws ItemException, FoodException {
        // Test represent scenario where foodDTO has expected data
        FoodDTO foodDTO = createFoodDTO();
        Food foodMocked = new Food(foodDTO);
        Category category = new Category(foodDTO.getCategory());
        given(categoryServiceMock.findOne(CATEGORY_ID)).willReturn(category);
        given(categoryServiceMock.insertCategory(foodDTO.getCategory())).willReturn(category);
        given(foodRepositoryMock.save(foodMocked)).willReturn(foodMocked);

        // Test invoke
        Food food = foodService.saveFood(foodDTO);

        // Verifying
        verify(categoryServiceMock, times(1)).findOne(CATEGORY_ID);
        verify(categoryServiceMock, times(0)).insertCategory(foodDTO.getCategory());
        verify(foodRepositoryMock, times(1)).save(food);

        assertNotNull(food);
        assertEquals(foodDTO.getName(), food.getName());
        assertEquals(foodDTO.getCategory().getName(), food.getCategory().getName());
    }

    @Test
    public void testSaveFood_RecipeNull() {
        FoodDTO foodDTO = createFoodDTO();
        // Recipe is null
        foodDTO.setRecipe(null);
        Food foodMocked = new Food(foodDTO);
        given(foodRepositoryMock.save(foodMocked)).willReturn(foodMocked);

        // Test invoke
        Exception exception = assertThrows(FoodException.class, () -> foodService.saveFood(foodDTO));

        // Verifying
        assertEquals(NULL_VALUES, exception.getMessage());
    }

    @Test
    public void testSaveFood_FoodTypeNull() {
        FoodDTO foodDTO = createFoodDTO();
        // FoodType is null
        foodDTO.setType(null);
        Food foodMocked = new Food(foodDTO);
        given(foodRepositoryMock.save(foodMocked)).willReturn(foodMocked);

        // Test invoke
        Exception exception = assertThrows(FoodException.class, () -> foodService.saveFood(foodDTO));

        // Verifying
        assertEquals(NULL_VALUES, exception.getMessage());
    }

    @Test
    public void testSaveFood_RecipeContentBlank() {
        FoodDTO foodDTO = createFoodDTO();
        // Recipe is blank
        foodDTO.setRecipe("");
        Food foodMocked = new Food(foodDTO);
        given(foodRepositoryMock.save(foodMocked)).willReturn(foodMocked);

        // Test invoke
        Exception exception = assertThrows(FoodException.class, () -> foodService.saveFood(foodDTO));

        // Verifying
        assertEquals(RECIPE_CONTENT_BLANK, exception.getMessage());

    }


    private FoodDTO createFoodDTO(){
        // This method creates testing object
        CategoryDTO category = new CategoryDTO(1L, "Supe");
        return new FoodDTO(1L, "Supa", 250.0, "Bas je slana", "putanja/supa", category, ItemType.FOOD, false, "Ma lako se pravi", 20, "APPETIZER");
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
