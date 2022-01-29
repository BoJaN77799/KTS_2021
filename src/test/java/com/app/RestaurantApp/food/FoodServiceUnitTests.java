package com.app.RestaurantApp.food;

import com.app.RestaurantApp.category.Category;
import com.app.RestaurantApp.category.CategoryService;
import com.app.RestaurantApp.category.dto.CategoryDTO;
import com.app.RestaurantApp.enums.ItemType;
import com.app.RestaurantApp.food.dto.FoodDTO;
import com.app.RestaurantApp.food.dto.FoodSearchDTO;
import com.app.RestaurantApp.food.dto.FoodWithIngredientsDTO;
import com.app.RestaurantApp.ingredient.Ingredient;
import com.app.RestaurantApp.ingredient.dto.IngredientDTO;
import com.app.RestaurantApp.item.ItemException;
import com.app.RestaurantApp.order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import java.util.*;

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

        given(foodRepositoryMock.findAllWithPriceByCriteria(NAME2, ALL, ALL, STALNI_MENI, pageableSetup)).willReturn(foodsPageSetup);
        given(foodRepositoryMock.findAllWithPriceByCriteria(ALL, CATEGORY1, ALL, STALNI_MENI, pageableSetup)).willReturn(foodsPageSetup);
        given(foodRepositoryMock.findAllWithPriceByCriteria(ALL, ALL, MAIN_DISH, STALNI_MENI, pageableSetup)).willReturn(foodsPageSetup);
        given(foodRepositoryMock.findAllWithPriceByCriteria(ALL, ALL, ALL, STALNI_MENI, pageableSetup)).willReturn(foodsPageSetup);

        Category category = new Category(1L, "Supe");
        given(categoryServiceMock.findOne(category.getId())).willReturn(category);
        given(categoryServiceMock.insertCategory(category)).willReturn(category);
    }

    @Test
    public void testGetFoodWithPrice_WithName() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO(NAME2, ALL, ALL, BLANK);
        Page<Food> foodsPage = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        verify(foodRepositoryMock, times(1)).findAllWithPriceByCriteria(NAME2, ALL, ALL, STALNI_MENI, pageable);
        assertEquals(2, foodsPage.getNumberOfElements());
    }

    @Test
    public void testGetFoodWithPrice_WithCategory() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO(ALL, CATEGORY1, ALL, BLANK);
        Page<Food> foodsPage = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        verify(foodRepositoryMock, times(1)).findAllWithPriceByCriteria(ALL, CATEGORY1, ALL, STALNI_MENI, pageable);
        assertEquals(2, foodsPage.getNumberOfElements());
    }

    @Test
    public void testGetFoodWithPrice_WithType() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO(ALL, ALL, TYPE1, BLANK);
        Page<Food> foodsPage = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        verify(foodRepositoryMock, times(1)).findAllWithPriceByCriteria(ALL, ALL, TYPE1, STALNI_MENI, pageable);
        assertEquals(2, foodsPage.getNumberOfElements());
    }

    @Test
    public void testGetFoodWithPrice_WithNulls() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO(null, null, null, null);
        Page<Food> foodsPage = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        verify(foodRepositoryMock, times(1)).findAllWithPriceByCriteria(ALL, ALL, ALL, STALNI_MENI, pageable);
        assertEquals(2, foodsPage.getNumberOfElements());
    }

    @Test
    public void testGetFoodWithPrice_WithBlanks() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        FoodSearchDTO foodSearchDTO = new FoodSearchDTO("", "", "", "");
        Page<Food> foodsPage = foodService.getFoodWithPrice(foodSearchDTO, pageable);
        verify(foodRepositoryMock, times(1)).findAllWithPriceByCriteria(ALL, ALL, ALL, STALNI_MENI, pageable);
        assertEquals(2, foodsPage.getNumberOfElements());
    }

    @Test
    public void testSaveFood() throws ItemException, FoodException {
        // Test represent scenario where foodDTO has expected data
        FoodWithIngredientsDTO foodDTO = createFoodDTO();
        Food foodMocked = new Food(foodDTO);
        Category category = new Category(foodDTO.getCategory());
        given(categoryServiceMock.findOneByName(CATEGORY_NAME)).willReturn(category);
        given(foodRepositoryMock.save(foodMocked)).willReturn(foodMocked);

        // Test invoke
        Food food = foodService.saveFood(foodDTO);

        // Verifying
        verify(categoryServiceMock, times(1)).findOneByName(CATEGORY_NAME);
        verify(categoryServiceMock, times(0)).insertCategory(category);
        verify(foodRepositoryMock, times(1)).save(food);

        assertNotNull(food);
        assertEquals(foodDTO.getName(), food.getName());
        assertEquals(foodDTO.getCategory(), food.getCategory().getName());
    }

    @Test
    public void testSaveFood_RecipeNull() {
        FoodWithIngredientsDTO foodDTO = createFoodDTO();
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
        FoodWithIngredientsDTO foodDTO = createFoodDTO();
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
        FoodWithIngredientsDTO foodDTO = createFoodDTO();
        // Recipe is blank
        foodDTO.setRecipe("");
        Food foodMocked = new Food(foodDTO);
        given(foodRepositoryMock.save(foodMocked)).willReturn(foodMocked);

        // Test invoke
        Exception exception = assertThrows(FoodException.class, () -> foodService.saveFood(foodDTO));

        // Verifying
        assertEquals(RECIPE_CONTENT_BLANK, exception.getMessage());
    }

    @Test
    public void testSaveFood_NameNull() {
        FoodWithIngredientsDTO foodDTO = createFoodDTO();
        // Name is null
        foodDTO.setName(null);
        Food foodMocked = new Food(foodDTO);
        given(foodRepositoryMock.save(foodMocked)).willReturn(foodMocked);

        // Test invoke
        Exception exception = assertThrows(ItemException.class, () -> foodService.saveFood(foodDTO));

        // Verifying
        assertEquals(NULL_VALUES_ITEM, exception.getMessage());
    }

    @Test
    public void testSaveFood_DescriptionNull() {
        FoodWithIngredientsDTO foodDTO = createFoodDTO();
        // Description is null
        foodDTO.setDescription(null);
        Food foodMocked = new Food(foodDTO);
        given(foodRepositoryMock.save(foodMocked)).willReturn(foodMocked);

        // Test invoke
        Exception exception = assertThrows(ItemException.class, () -> foodService.saveFood(foodDTO));

        // Verifying
        assertEquals(NULL_VALUES_ITEM, exception.getMessage());
    }

    @Test
    public void testSaveFood_ImageNull() {
        FoodWithIngredientsDTO foodDTO = createFoodDTO();
        // Image is null
        foodDTO.setImage(null);
        Food foodMocked = new Food(foodDTO);
        given(foodRepositoryMock.save(foodMocked)).willReturn(foodMocked);

        // Test invoke
        Exception exception = assertThrows(ItemException.class, () -> foodService.saveFood(foodDTO));

        // Verifying
        assertEquals(NULL_VALUES_ITEM, exception.getMessage());
    }

    @Test
    public void testSaveFood_NewCategory() throws ItemException, FoodException {
        FoodWithIngredientsDTO foodDTO = createFoodDTO();
        // Category is new one.
        Food foodMocked = new Food(foodDTO);
        Category category = new Category(CATEGORY_ID, CATEGORY_NAME);
        given(categoryServiceMock.findOneByName(CATEGORY_NAME)).willReturn(null); // category does not exist
        given(categoryServiceMock.insertCategory(any(Category.class))).willReturn(category);
        given(foodRepositoryMock.save(foodMocked)).willReturn(foodMocked);

        // Test invoke
        Food food = foodService.saveFood(foodDTO);

        // Verifying
        verify(categoryServiceMock, times(1)).findOneByName(CATEGORY_NAME);
        verify(categoryServiceMock, times(1)).insertCategory(any(Category.class));
        verify(foodRepositoryMock, times(1)).save(food);

        assertNotNull(food);
        assertEquals(foodDTO.getName(), food.getName());
        assertEquals(category.getName(), food.getCategory().getName());
    }

    @Test
    public void testSaveFood_ItemTypeNull() {
        FoodWithIngredientsDTO foodDTO = createFoodDTO();
        // ItemType is null
        foodDTO.setItemType(null);
        Food foodMocked = new Food(foodDTO);
        given(foodRepositoryMock.save(foodMocked)).willReturn(foodMocked);

        // Test invoke
        Exception exception = assertThrows(ItemException.class, () -> foodService.saveFood(foodDTO));

        // Verifying
        assertEquals(NULL_VALUES_ITEM, exception.getMessage());
    }

    @Test
    public void testSaveFood_NameContentBlank() {
        FoodWithIngredientsDTO foodDTO = createFoodDTO();
        // Name is blank
        foodDTO.setName("");
        Food foodMocked = new Food(foodDTO);
        given(foodRepositoryMock.save(foodMocked)).willReturn(foodMocked);

        // Test invoke
        Exception exception = assertThrows(ItemException.class, () -> foodService.saveFood(foodDTO));

        // Verifying
        assertEquals(NAME_CONTENT_BLANK, exception.getMessage());
    }

    @Test
    public void testSaveFood_CostEqualsOrLowerThanZero() {
        FoodWithIngredientsDTO foodDTO = createFoodDTO();
        // Cost is lower than 0
        foodDTO.setCost(-30.0);
        Food foodMocked = new Food(foodDTO);
        given(foodRepositoryMock.save(foodMocked)).willReturn(foodMocked);

        // Test invoke
        Exception exception = assertThrows(ItemException.class, () -> foodService.saveFood(foodDTO));

        // Verifying
        assertEquals(COST_EQUALS_OR_LOWER_THAN_ZERO, exception.getMessage());
    }

    @Test
    public void testSaveFood_DescriptionContentBlank() {
        FoodWithIngredientsDTO foodDTO = createFoodDTO();
        // Description is blank
        foodDTO.setDescription("");
        Food foodMocked = new Food(foodDTO);
        given(foodRepositoryMock.save(foodMocked)).willReturn(foodMocked);

        // Test invoke
        Exception exception = assertThrows(ItemException.class, () -> foodService.saveFood(foodDTO));

        // Verifying
        assertEquals(DESCRIPTION_CONTENT_BLANK, exception.getMessage());
    }

    @Test
    public void testSaveFood_DescriptionContentIsTooBig() {
        FoodWithIngredientsDTO foodDTO = createFoodDTO();
        // Description is too big
        foodDTO.setDescription("a".repeat(257)); // 257 characters
        Food foodMocked = new Food(foodDTO);
        given(foodRepositoryMock.save(foodMocked)).willReturn(foodMocked);

        // Test invoke
        Exception exception = assertThrows(ItemException.class, () -> foodService.saveFood(foodDTO));

        // Verifying
        assertEquals(DESCRIPTION_TOO_BIG, exception.getMessage());
    }

    @Test
    public void testSaveFood_ImagePathBlank() {
        FoodWithIngredientsDTO foodDTO = createFoodDTO();
        // Image path is blank
        foodDTO.setImage("");
        Food foodMocked = new Food(foodDTO);
        given(foodRepositoryMock.save(foodMocked)).willReturn(foodMocked);

        // Test invoke
        Exception exception = assertThrows(ItemException.class, () -> foodService.saveFood(foodDTO));

        // Verifying
        assertEquals(IMAGE_PATH_BLANK, exception.getMessage());
    }

    @Test
    public void testSaveFood_CategoryNameBlank() {
        FoodWithIngredientsDTO foodDTO = createFoodDTO();
        // Category name is blank
        CategoryDTO blankCategory = new CategoryDTO(1L, "");
        foodDTO.setCategory(blankCategory.getName());
        Food foodMocked = new Food(foodDTO);
        given(foodRepositoryMock.save(foodMocked)).willReturn(foodMocked);

        // Test invoke
        Exception exception = assertThrows(ItemException.class, () -> foodService.saveFood(foodDTO));

        // Verifying
        assertEquals(CATEGORY_NAME_BLANK, exception.getMessage());
    }

    private FoodWithIngredientsDTO createFoodDTO(){
        // This method creates testing object
        CategoryDTO category = new CategoryDTO(1L, "Supe");
        HashSet<IngredientDTO> ingredients = new HashSet<>(List.of(
                new IngredientDTO(3L, "Brasno", false),
                new IngredientDTO(6L, "Voda", false)));
        return new FoodWithIngredientsDTO(1L, "Supa", 250.0, "Bas je slana", "putanja/supa", category, ItemType.FOOD, false, "Ma lako se pravi", 20, "APPETIZER", ingredients);
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
