package com.app.RestaurantApp.food;


import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import static com.app.RestaurantApp.food.Constants.*;
import static org.junit.Assert.assertEquals;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FoodRepositoryIntegrationTests {

    @Autowired
    private FoodRepository foodRepository;

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
        Page<Food> foodsPage;
        List<Food> foods;

        foodsPage = foodRepository.findAllWithPriceByCriteria(NAME1, ALL, ALL, pageable);
        foods = foodsPage.getContent();
        assertEquals(1, foodsPage.getTotalElements());
        assertEquals("Supa", foods.get(0).getName());

        foodsPage = foodRepository.findAllWithPriceByCriteria(NAME2,  ALL, ALL, pageable);
        foods = foodsPage.getContent();
        assertEquals(1, foodsPage.getTotalElements());
        assertEquals("Jagnjece pecenje", foods.get(0).getName());

        foodsPage = foodRepository.findAllWithPriceByCriteria(ALL,  ALL, APPETIZER, pageable);
        foods = foodsPage.getContent();
        assertEquals(3, foodsPage.getTotalElements());
        assertEquals("Supa", foods.get(0).getName());

        foodsPage = foodRepository.findAllWithPriceByCriteria(NAME2,  ALL, MAIN_DISH, pageable);
        foods = foodsPage.getContent();
        assertEquals(1, foodsPage.getTotalElements());
        assertEquals("Jagnjece pecenje", foods.get(0).getName());

        foodsPage = foodRepository.findAllWithPriceByCriteria(ALL,  CATEGORY1, ALL, pageable);
        foods = foodsPage.getContent();
        assertEquals(2, foodsPage.getTotalElements());
        assertEquals("Prsuta", foods.get(0).getName());

        foodsPage = foodRepository.findAllWithPriceByCriteria(NAME2,  CATEGORY1, MAIN_DISH, pageable);
        foods = foodsPage.getContent();
        assertEquals(1, foodsPage.getTotalElements());
        assertEquals("Jagnjece pecenje", foods.get(0).getName());
    }
}