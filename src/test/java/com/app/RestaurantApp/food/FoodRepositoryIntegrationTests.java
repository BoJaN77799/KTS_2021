package com.app.RestaurantApp.food;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import static com.app.RestaurantApp.food.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class FoodRepositoryIntegrationTests {

    /*
        Ukoliko test pada proveriti da li su mozda dodati novi entiteti u bazu koji odgovaraju pretrazi!
    */

    @Autowired
    private FoodRepository foodRepository;

    @Test
    public void testFindAllWithPriceByCriteria() {

        Page<Food> foodsPage;
        List<Food> foods;
        Pageable pg = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);

        foodsPage = foodRepository.findAllWithPriceByCriteria(NAME2,  CATEGORY1, MAIN_DISH, STALNI_MENI, pg);
        foods = foodsPage.getContent();
        assertEquals(1, foodsPage.getTotalElements());
        assertEquals("Jagnjece pecenje", foods.get(0).getName());
    }

    @Test
    public void testFindAllWithPriceByCriteria_WithName() {
        Page<Food> foodsPage;
        List<Food> foods;
        Pageable pg = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);

        foodsPage = foodRepository.findAllWithPriceByCriteria(NAME1, ALL, ALL, STALNI_MENI, pg);
        foods = foodsPage.getContent();
        assertEquals(1, foodsPage.getTotalElements());
        assertEquals("Supa", foods.get(0).getName());
    }

    @Test
    public void testFindAllWithPriceByCriteria_WithType() {
        Page<Food> foodsPage;
        List<Food> foods;
        Pageable pg = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);

        foodsPage = foodRepository.findAllWithPriceByCriteria(ALL,  ALL, APPETIZER, STALNI_MENI, pg);
        foods = foodsPage.getContent();
        assertEquals(3, foodsPage.getTotalElements());
        assertEquals("Supa", foods.get(0).getName());
    }

    @Test
    public void testFindAllWithPriceByCriteria_WithCategory() {
        Page<Food> foodsPage;
        List<Food> foods;
        Pageable pg = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);

        foodsPage = foodRepository.findAllWithPriceByCriteria(ALL,  CATEGORY1, ALL, STALNI_MENI, pg);
        foods = foodsPage.getContent();
        assertEquals(2, foodsPage.getTotalElements());
        assertEquals("Prsuta", foods.get(0).getName());
    }

    @Test
    public void testFindAllWithPriceByCriteria_WithNameAndCategory() {
        Page<Food> foodsPage;
        List<Food> foods;
        Pageable pg = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);

        foodsPage = foodRepository.findAllWithPriceByCriteria(NAME3, CATEGORY1, ALL, STALNI_MENI, pg);
        foods = foodsPage.getContent();
        assertEquals(2, foodsPage.getTotalElements());
        assertEquals("Prsuta", foods.get(0).getName());
    }

    @Test
    public void testFindAllWithPriceByCriteria_WithNameAndType() {
        Page<Food> foodsPage;
        List<Food> foods;
        Pageable pg = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);

        foodsPage = foodRepository.findAllWithPriceByCriteria(NAME2,  ALL, MAIN_DISH, STALNI_MENI, pg);
        foods = foodsPage.getContent();
        assertEquals(1, foodsPage.getTotalElements());
        assertEquals("Jagnjece pecenje", foods.get(0).getName());
    }

    @Test
    public void testFindAllWithPriceByCriteria_WithBlanks() {
        Page<Food> foodsPage;
        List<Food> foods;
        Pageable pg = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);

        foodsPage = foodRepository.findAllWithPriceByCriteria(ALL,  ALL, ALL, STALNI_MENI, pg);
        foods = foodsPage.getContent();
        assertEquals(5, foodsPage.getTotalElements());
        assertEquals("Supa", foods.get(0).getName());
    }

    @Test
    public void testFindAllWithPriceByCriteria_WithBlanksSpecialMenu() {
        Page<Food> foodsPage;
        List<Food> foods;
        Pageable pg = PageRequest.of(PAGEABLE_PAGE, PAGEABLE_SIZE);

        foodsPage = foodRepository.findAllWithPriceByCriteria(ALL,  ALL, ALL, SPECIJALNI_MENI, pg);
        foods = foodsPage.getContent();
        assertEquals(1, foodsPage.getTotalElements());
        assertEquals("Sladoled Kapri", foods.get(0).getName());
    }

    @Test
    public void testfindAllFoodCategories() {
        List<String> categories = foodRepository.findAllFoodCategories();

        assertEquals(categories.size(), 5);
    }
}