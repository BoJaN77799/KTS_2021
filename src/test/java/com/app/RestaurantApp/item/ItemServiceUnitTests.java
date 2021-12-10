package com.app.RestaurantApp.item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ItemServiceUnitTests {

    @Autowired
    private ItemService itemService;

    @MockBean
    private ItemRepository itemRepository;

    @BeforeEach
    public void setup() {

    }

    @Test
    public void testCreateUpdatePriceOnItem_ValidCreating() {

    }

    @Test
    public void testCreateUpdatePriceOnItem_ValidUpdating() {

    }

    @Test
    public void testCreateUpdatePriceOnItem_InvalidItem() {

    }

    @Test
    public void testCreateUpdatePriceOnItem_InvalidPrice() {

    }

    @Test
    public void testGetPricesOfItem_Valid() {

    }

    @Test
    public void testGetPricesOfItem_InvalidItem() {

    }
}
