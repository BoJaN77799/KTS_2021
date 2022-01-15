package com.app.RestaurantApp.category;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static com.app.RestaurantApp.category.Constants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoryServiceIntegrationTests {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void testFindOne() {
        // Test invoke
        Category category = categoryService.findOne(CATEGORY_ID);

        // Verifying
        assertNotNull(category);
        assertEquals(FOUND_CATEGORY_NAME, category.getName());
    }

    @Test
    public void testFindOneByName() {
        // Test invoke
        Category category = categoryService.findOneByName(CATEGORY_NAME);

        // Verifying
        assertNotNull(category);
        assertEquals(CATEGORY_NAME, category.getName());
        assertEquals(FOUND_CATEGORY_ID, category.getId());
    }

    @Test
    public void testInsertCategory() {
        Category category = new Category("Kolaci");
        // Test invoke
        category = categoryService.insertCategory(category);

        // Verifying
        assertNotNull(category);
        assertEquals(INSERTED_CATEGORY_NAME, category.getName());
        assertEquals(INSERTED_CATEGORY_ID, category.getId());
    }
}
