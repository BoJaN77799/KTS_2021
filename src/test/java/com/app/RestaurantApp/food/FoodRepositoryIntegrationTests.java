package com.app.RestaurantApp.food;

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


import static com.app.RestaurantApp.food.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class FoodRepositoryIntegrationTests {

    @Autowired
    private FoodRepository foodRepository;

    @Test
    public void testFindAllWithPriceByCriteria() {
        /*
            Ukoliko test pada proveriti da li su mozda dodati novi entiteti u bazu koji odgovaraju pretrazi!
        */
        Page<Food> foodsPage;
        List<Food> foods;

        Pageable pg = PageRequest.of(0, 5);

        foodsPage = foodRepository.findAllWithPriceByCriteria(NAME1, ALL, ALL, pg);
        foods = foodsPage.getContent();
        assertEquals(1, foodsPage.getTotalElements());
        assertEquals("Supa", foods.get(0).getName());

        foodsPage = foodRepository.findAllWithPriceByCriteria(NAME2,  ALL, ALL, pg);
        foods = foodsPage.getContent();
        assertEquals(1, foodsPage.getTotalElements());
        assertEquals("Jagnjece pecenje", foods.get(0).getName());

        foodsPage = foodRepository.findAllWithPriceByCriteria(ALL,  ALL, APPETIZER, pg);
        foods = foodsPage.getContent();
        assertEquals(3, foodsPage.getTotalElements());
        assertEquals("Supa", foods.get(0).getName());

        foodsPage = foodRepository.findAllWithPriceByCriteria(NAME2,  ALL, MAIN_DISH, pg);
        foods = foodsPage.getContent();
        assertEquals(1, foodsPage.getTotalElements());
        assertEquals("Jagnjece pecenje", foods.get(0).getName());

        foodsPage = foodRepository.findAllWithPriceByCriteria(ALL,  CATEGORY1, ALL, pg);
        foods = foodsPage.getContent();
        assertEquals(2, foodsPage.getTotalElements());
        assertEquals("Prsuta", foods.get(0).getName());

        foodsPage = foodRepository.findAllWithPriceByCriteria(NAME2,  CATEGORY1, MAIN_DISH, pg);
        foods = foodsPage.getContent();
        assertEquals(1, foodsPage.getTotalElements());
        assertEquals("Jagnjece pecenje", foods.get(0).getName());
    }
}