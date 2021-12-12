package com.app.RestaurantApp.item;

import com.app.RestaurantApp.item.dto.ItemPriceDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.app.RestaurantApp.item.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemServiceIntegrationTests {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    @Transactional
    public void testFindAllWithIds() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        List<Item> items = itemService.findAllWithIds(ids);
        assertEquals(3, items.size());

        ids = Arrays.asList(4L, 5L);
        items = itemService.findAllWithIds(ids);
        assertEquals(2, items.size());

        ids = Arrays.asList(11L, 12L);
        items = itemService.findAllWithIds(ids);
        assertEquals(0, items.size());
    }

    @Test
    @Transactional
    public void testFindItemById() {
        Item i = itemService.findItemById(VALID_ID);
        assertNotNull(i);

        i = itemService.findItemById(INVALID_ID);
        assertNull(i);
    }

    @Test
    @Transactional
    public void testCreateUpdatePriceOnItem() throws ItemException {
        ItemPriceDTO itemPriceDTO = new ItemPriceDTO();
        itemPriceDTO.setId(VALID_ID);
        itemPriceDTO.setNewPrice(10000);

        assertFalse(itemService.createUpdatePriceOnItem(itemPriceDTO));
        double newPrice = Objects.requireNonNull(itemRepository.findById(VALID_ID).orElse(null)).getCurrentPrice();
        assertEquals(itemPriceDTO.getNewPrice(), newPrice);
    }

    @Test
    @Transactional
    public void testGetPricesOfItem() throws ItemException {
        List<ItemPriceDTO> prices = itemService.getPricesOfItem("1");
        assertEquals(4, prices.size());

        prices = itemService.getPricesOfItem("2");
        assertEquals(1, prices.size());
    }
}
