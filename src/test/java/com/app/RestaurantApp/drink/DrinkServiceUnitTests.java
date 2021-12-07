package com.app.RestaurantApp.drink;

import com.app.RestaurantApp.drinks.Drink;
import com.app.RestaurantApp.drinks.DrinkRepository;
import com.app.RestaurantApp.drinks.DrinkService;
import com.app.RestaurantApp.drinks.dto.DrinkSearchDTO;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DrinkServiceUnitTests {

    @Autowired
    private DrinkService drinkService;

    @MockBean
    private DrinkRepository drinkRepository;

    @BeforeEach
    public void setup() {
        Pageable pageableSetup = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        List<Drink> drinksSetup = createDrinks();
        Page<Drink> drinksPageSetup = new PageImpl<>(drinksSetup, pageableSetup, PAGEABLE_TOTAL_ELEMENTS);

        given(drinkRepository.findAllWithPriceByCriteria(NAME3, ALL, pageableSetup)).willReturn(drinksPageSetup);
        given(drinkRepository.findAllWithPriceByCriteria(ALL, CATEGORY2, pageableSetup)).willReturn(drinksPageSetup);
        given(drinkRepository.findAllWithPriceByCriteria(ALL, ALL, pageableSetup)).willReturn(drinksPageSetup);
    }

    @Test
    public void testGetDrinksWithPrice_WithName() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        DrinkSearchDTO drinkSearchDTO = new DrinkSearchDTO(NAME3, ALL);
        Page<Drink> drinksPage = drinkService.getDrinksWithPrice(drinkSearchDTO, pageable);
        verify(drinkRepository, times(1)).findAllWithPriceByCriteria(NAME3, ALL, pageable);
        assertEquals(2, drinksPage.getNumberOfElements());
    }

    @Test
    public void testGetDrinksWithPrice_WithCategory() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        DrinkSearchDTO drinkSearchDTO = new DrinkSearchDTO(ALL, CATEGORY2);
        Page<Drink> drinksPage = drinkService.getDrinksWithPrice(drinkSearchDTO, pageable);
        verify(drinkRepository, times(1)).findAllWithPriceByCriteria(ALL, CATEGORY2, pageable);
        assertEquals(2, drinksPage.getNumberOfElements());
    }

    @Test
    public void testGetDrinksWithPrice_WithBlanks() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        DrinkSearchDTO drinkSearchDTO = new DrinkSearchDTO(ALL, ALL);
        Page<Drink> drinksPage = drinkService.getDrinksWithPrice(drinkSearchDTO, pageable);
        verify(drinkRepository, times(1)).findAllWithPriceByCriteria(ALL, ALL, pageable);
        assertEquals(2, drinksPage.getNumberOfElements());
    }

    @Test
    public void testGetDrinksWithPrice_WithNulls() {
        Pageable pageable = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);
        DrinkSearchDTO drinkSearchDTO = new DrinkSearchDTO(null, null);
        Page<Drink> drinksPage = drinkService.getDrinksWithPrice(drinkSearchDTO, pageable);
        verify(drinkRepository, times(1)).findAllWithPriceByCriteria(ALL, ALL, pageable);
        assertEquals(2, drinksPage.getNumberOfElements());
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
