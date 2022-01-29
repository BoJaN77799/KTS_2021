package com.app.RestaurantApp.drink;

import com.app.RestaurantApp.category.Category;
import com.app.RestaurantApp.category.CategoryService;
import com.app.RestaurantApp.category.dto.CategoryDTO;
import com.app.RestaurantApp.drinks.Drink;
import com.app.RestaurantApp.drinks.DrinkException;
import com.app.RestaurantApp.drinks.DrinkRepository;
import com.app.RestaurantApp.drinks.DrinkService;
import com.app.RestaurantApp.drinks.dto.DrinkCreateDTO;
import com.app.RestaurantApp.drinks.dto.DrinkDTO;
import com.app.RestaurantApp.drinks.dto.DrinkSearchDTO;
import com.app.RestaurantApp.enums.ItemType;
import com.app.RestaurantApp.item.ItemException;
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

import java.util.ArrayList;
import java.util.List;

import static com.app.RestaurantApp.drink.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DrinkServiceUnitTests {

    @Autowired
    private DrinkService drinkService;

    @MockBean
    private CategoryService categoryServiceMock;

    @MockBean
    private DrinkRepository drinkRepositoryMock;

    @BeforeEach
    public void setup() {
        Pageable pageableSetup = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        List<Drink> drinksSetup = createDrinks();
        Page<Drink> drinksPageSetup = new PageImpl<>(drinksSetup, pageableSetup, PAGEABLE_TOTAL_ELEMENTS);

        given(drinkRepositoryMock.findAllWithPriceByCriteria(NAME3, ALL, LETNJI_MENI, pageableSetup)).willReturn(drinksPageSetup);
        given(drinkRepositoryMock.findAllWithPriceByCriteria(ALL, CATEGORY2, LETNJI_MENI, pageableSetup)).willReturn(drinksPageSetup);
        given(drinkRepositoryMock.findAllWithPriceByCriteria(ALL, ALL, LETNJI_MENI, pageableSetup)).willReturn(drinksPageSetup);
    }

    @Test
    public void testGetDrinksWithPrice_WithName() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        DrinkSearchDTO drinkSearchDTO = new DrinkSearchDTO(NAME3, ALL, LETNJI_MENI);
        Page<Drink> drinksPage = drinkService.getDrinksWithPrice(drinkSearchDTO, pageable);
        verify(drinkRepositoryMock, times(1)).findAllWithPriceByCriteria(NAME3, ALL, LETNJI_MENI, pageable);
        assertEquals(2, drinksPage.getNumberOfElements());
    }

    @Test
    public void testGetDrinksWithPrice_WithCategory() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        DrinkSearchDTO drinkSearchDTO = new DrinkSearchDTO(ALL, CATEGORY2, LETNJI_MENI);
        Page<Drink> drinksPage = drinkService.getDrinksWithPrice(drinkSearchDTO, pageable);
        verify(drinkRepositoryMock, times(1)).findAllWithPriceByCriteria(ALL, CATEGORY2, LETNJI_MENI, pageable);
        assertEquals(2, drinksPage.getNumberOfElements());
    }

    @Test
    public void testGetDrinksWithPrice_WithBlanksOtherMenu() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        DrinkSearchDTO drinkSearchDTO = new DrinkSearchDTO(ALL, ALL, LETNJI_MENI);
        Page<Drink> drinksPage = drinkService.getDrinksWithPrice(drinkSearchDTO, pageable);
        verify(drinkRepositoryMock, times(1)).findAllWithPriceByCriteria(ALL, ALL, LETNJI_MENI, pageable);
        assertEquals(2, drinksPage.getNumberOfElements());
    }

    @Test
    public void testGetDrinksWithPrice_WithNullsOtherMenu() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        DrinkSearchDTO drinkSearchDTO = new DrinkSearchDTO(null, null, LETNJI_MENI);
        Page<Drink> drinksPage = drinkService.getDrinksWithPrice(drinkSearchDTO, pageable);
        verify(drinkRepositoryMock, times(1)).findAllWithPriceByCriteria(ALL, ALL, LETNJI_MENI, pageable);
        assertEquals(2, drinksPage.getNumberOfElements());
    }

    @Test
    public void testSaveDrink() throws ItemException, DrinkException {
        // Test represent scenario where drinkDTO has expected data
        DrinkCreateDTO drinkDTO = createDrinkDTO();
        Drink drinkMocked = new Drink(drinkDTO);
        Category category = new Category(drinkDTO.getCategory());
        given(categoryServiceMock.findOneByName(CATEGORY_NAME)).willReturn(category);
        given(categoryServiceMock.insertCategory(category)).willReturn(category);
        given(drinkRepositoryMock.save(any(Drink.class))).willReturn(drinkMocked);

        // Test invoke
        Drink drink = drinkService.saveDrink(drinkDTO);

        // Verifying
        verify(categoryServiceMock, times(1)).findOneByName(CATEGORY_NAME);
        verify(categoryServiceMock, times(0)).insertCategory(category);
        verify(drinkRepositoryMock, times(1)).save(any(Drink.class));

        assertNotNull(drink);
        assertEquals(drinkDTO.getVolume(), drink.getVolume());
        assertEquals(drinkDTO.getCost(), drink.getCost());
        assertEquals(drinkDTO.getCategory(), drink.getCategory().getName());
    }

    @Test
    public void testSaveDrink_NewCategory() throws ItemException, DrinkException {
        // Test represent scenario where drinkDTO has expected data
        DrinkCreateDTO drinkDTO = createDrinkDTO();
        Drink drinkMocked = new Drink(drinkDTO);
        Category category = new Category(CATEGORY_ID, CATEGORY_NAME);
        given(categoryServiceMock.findOneByName(CATEGORY_NAME)).willReturn(null); // category does not exist
        given(categoryServiceMock.insertCategory(any(Category.class))).willReturn(category);
        given(drinkRepositoryMock.save(any(Drink.class))).willReturn(drinkMocked);

        // Test invoke
        Drink drink = drinkService.saveDrink(drinkDTO);

        // Verifying
        verify(categoryServiceMock, times(1)).findOneByName(CATEGORY_NAME);
        verify(categoryServiceMock, times(1)).insertCategory(any(Category.class));
        verify(drinkRepositoryMock, times(1)).save(any(Drink.class));

        assertNotNull(drink);
        assertEquals(drinkDTO.getVolume(), drink.getVolume());
        assertEquals(drinkDTO.getCost(), drink.getCost());
        assertEquals(CATEGORY_NAME, drink.getCategory().getName());
    }

    @Test
    public void testSaveDrink_VolumeEqualsOrLowerThanZero() {
        DrinkCreateDTO drinkDTO = createDrinkDTO();
        // Volume is lower than zero
        drinkDTO.setVolume(-0.33);
        Drink drinkMocked = new Drink(drinkDTO);
        given(drinkRepositoryMock.save(drinkMocked)).willReturn(drinkMocked);

        // Test invoke
        Exception exception = assertThrows(DrinkException.class, () -> drinkService.saveDrink(drinkDTO));

        // Verifying
        assertEquals(VOLUME_EQUALS_OR_LOWER_THAN_ZERO, exception.getMessage());
    }

    private DrinkCreateDTO createDrinkDTO() {
        CategoryDTO category = new CategoryDTO(7L, "Alkoholna pica");
        return new DrinkCreateDTO(70L, "Coca cola", 140.0, "Bas je gazirana", "putanja/cola", category, ItemType.DRINK, false, 0.5);
    }

    private List<Drink> createDrinks() {
        List<Drink> drinks = new ArrayList<>();

        Drink drink1 = new Drink();
        drink1.setId(1L);
        drink1.setName("Coca cola");
        drinks.add(drink1);

        Drink drink2 = new Drink();
        drink2.setId(2L);
        drink2.setName("Light cola");
        drinks.add(drink2);

        return drinks;
    }
}
