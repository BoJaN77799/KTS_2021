package com.app.RestaurantApp.drink;

import com.app.RestaurantApp.drinks.Drink;
import com.app.RestaurantApp.drinks.DrinkRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.app.RestaurantApp.drink.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class DrinkRepositoryIntegrationTests {

    @Autowired
    private DrinkRepository drinkRepository;

    @Test
    public void testFindAllWithPriceByCriteria() {
        /*
            Ukoliko test pada proveriti da li su mozda dodati novi entiteti u bazu koji odgovaraju pretrazi!
        */
        Page<Drink> drinksPage;
        List<Drink> drinks;
        Pageable pg = PageRequest.of(0, 5);

        drinksPage = drinkRepository.findAllWithPriceByCriteria(NAME1, ALL, pg);
        drinks = drinksPage.getContent();
        assertEquals(2, drinks.size());
        assertEquals("Niksicko pivo", drinks.get(0).getName());
        assertEquals("Zajecarsko pivo", drinks.get(1).getName());

        drinksPage = drinkRepository.findAllWithPriceByCriteria(ALL, CATEGORY1, pg);
        drinks = drinksPage.getContent();
        assertEquals(3, drinks.size());
        assertEquals("Niksicko pivo", drinks.get(0).getName());
        assertEquals("Jelen", drinks.get(2).getName());

        drinksPage = drinkRepository.findAllWithPriceByCriteria(NAME2, CATEGORY1, pg);
        drinks = drinksPage.getContent();
        assertEquals(1, drinks.size());
        assertEquals("Zajecarsko pivo", drinks.get(0).getName());
    }


}