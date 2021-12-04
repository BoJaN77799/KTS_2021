package com.app.RestaurantApp.drink;

import com.app.RestaurantApp.drinks.Drink;
import com.app.RestaurantApp.drinks.DrinkRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.app.RestaurantApp.drink.Constants.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DrinkRepositoryIntegrationTests {

    @Autowired
    private DrinkRepository drinkRepository;

    private Pageable pageable;

    @BeforeAll
    public void setup() {
        pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 5;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public Pageable withPage(int pageNumber) {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };
    }

    @Test
    public void testFindAllWithPriceByCriteria() {
        /*
            Ukoliko test pada proveriti da li su mozda dodati novi entiteti u bazu koji odgovaraju pretrazi!
        */
        Page<Drink> drinksPage;
        List<Drink> drinks;

        drinksPage = drinkRepository.findAllWithPriceByCriteria(NAME1, ALL, pageable);
        drinks = drinksPage.getContent();
        assertEquals(2, drinks.size());
        assertEquals("Niksicko pivo", drinks.get(0).getName());
        assertEquals("Zajecarsko pivo", drinks.get(1).getName());

        drinksPage = drinkRepository.findAllWithPriceByCriteria(ALL, CATEGORY1, pageable);
        drinks = drinksPage.getContent();
        assertEquals(3, drinks.size());
        assertEquals("Niksicko pivo", drinks.get(0).getName());
        assertEquals("Jelen", drinks.get(2).getName());

        drinksPage = drinkRepository.findAllWithPriceByCriteria(NAME2, CATEGORY1, pageable);
        drinks = drinksPage.getContent();
        assertEquals(1, drinks.size());
        assertEquals("Zajecarsko pivo", drinks.get(0).getName());
    }


}
