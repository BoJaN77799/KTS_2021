package com.app.RestaurantApp.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ItemRepositoryTests {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testFindAllWithIds() {
        List<Long> ids = Arrays.asList(3L, 4L, 5L);
        List<Item> items = itemRepository.findAllWithIds(ids);
        assertEquals(3, items.size());

        ids = Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L);
        items = itemRepository.findAllWithIds(ids);
        assertEquals(10, items.size());

        ids = Arrays.asList(11L, 12L);
        items = itemRepository.findAllWithIds(ids);
        assertEquals(0, items.size());
    }

    @Test
    public void testFindByIdItemWithPrices() {
        Long firstId = 1L;
        Long secondId = 2L;
        Long unknownId = 11L;

        Item firstItem = itemRepository.findByIdItemWithPrices(firstId);
        assertNotNull(firstItem);
        assertEquals(4, firstItem.getPrices().size());

        Item secondItem = itemRepository.findByIdItemWithPrices(secondId);
        assertNotNull(secondItem);
        assertEquals(1, secondItem.getPrices().size());

        Item unknownItem = itemRepository.findByIdItemWithPrices(unknownId);
        assertNull(unknownItem);
    }
}
