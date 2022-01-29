package com.app.RestaurantApp.item;

import com.app.RestaurantApp.item.dto.ItemPriceDTO;
import com.app.RestaurantApp.item.dto.MenuItemDTO;
import com.app.RestaurantApp.menu.Menu;
import com.app.RestaurantApp.menu.MenuException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.app.RestaurantApp.item.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemServiceIntegrationTests {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void testFindAllWithIds() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        List<Item> items = itemService.findAllWithIds(ids);
        assertEquals(3, items.size());

        ids = Arrays.asList(4L, 5L);
        items = itemService.findAllWithIds(ids);
        assertEquals(2, items.size());

        ids = Arrays.asList(20L, 21L);
        items = itemService.findAllWithIds(ids);
        assertEquals(0, items.size());
    }

    @Test
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

        itemPriceDTO.setId(INVALID_ID);
        itemPriceDTO.setNewPrice(10000);

        Exception exception = assertThrows(ItemException.class, () -> itemService.createUpdatePriceOnItem(itemPriceDTO));
        assertEquals(ITEM_EXCEPTION, exception.getMessage());

        itemPriceDTO.setId(VALID_ID);
        itemPriceDTO.setNewPrice(-10000);

        exception = assertThrows(ItemException.class, () -> itemService.createUpdatePriceOnItem(itemPriceDTO));
        assertEquals(PRICE_EXCEPTION, exception.getMessage());
    }

    @Test
    public void testGetPricesOfItem() throws ItemException {
        List<ItemPriceDTO> prices = itemService.getPricesOfItem("1");
        assertEquals(4, prices.size());

        prices = itemService.getPricesOfItem("2");
        assertEquals(1, prices.size());

        Exception exception = assertThrows(ItemException.class, () -> itemService.getPricesOfItem("21"));
        assertEquals(ITEM_EXCEPTION, exception.getMessage());
    }

    @Test
    @Transactional
    public void testAddItemToMenu() throws MenuException, ItemException {
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setItemId("10");
        menuItemDTO.setMenuName(VALID_MENU);

        itemService.addItemToMenu(menuItemDTO);

        Optional<Item> i = itemRepository.findById(10L);
        assertTrue(i.isPresent());
        assertEquals(VALID_MENU, i.get().getMenu());

        menuItemDTO.setMenuName(NON_EXISTING_MENU);
        Exception exception = assertThrows(MenuException.class, () -> itemService.addItemToMenu(menuItemDTO));
        assertEquals(MENU_EXCEPTION, exception.getMessage());

        menuItemDTO.setMenuName(VALID_MENU);
        menuItemDTO.setItemId("22");
        exception = assertThrows(ItemException.class, () -> itemService.addItemToMenu(menuItemDTO));
        assertEquals(ITEM_EXCEPTION, exception.getMessage());

        menuItemDTO.setItemId(String.valueOf(VALID_ID));
        exception = assertThrows(MenuException.class, () -> itemService.addItemToMenu(menuItemDTO));
        assertEquals(ITEM_EXISTS_EXCEPTION, exception.getMessage());
    }

    @Test
    @Transactional
    public void testRemoveItemFromMenu() throws MenuException, ItemException {
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setItemId(String.valueOf(VALID_ID));
        menuItemDTO.setMenuName(VALID_MENU);

        itemService.removeItemFromMenu(menuItemDTO);

        Optional<Item> i = itemRepository.findById(VALID_ID);
        assertTrue(i.isPresent());
        assertEquals(UNDEFINED_NAME_MENU, i.get().getMenu());

        menuItemDTO.setMenuName(NON_EXISTING_MENU);
        Exception exception = assertThrows(MenuException.class, () -> itemService.removeItemFromMenu(menuItemDTO));
        assertEquals(MENU_EXCEPTION, exception.getMessage());

        menuItemDTO.setMenuName(VALID_MENU);
        menuItemDTO.setItemId("22");
        exception = assertThrows(ItemException.class, () -> itemService.removeItemFromMenu(menuItemDTO));
        assertEquals(ITEM_EXCEPTION, exception.getMessage());

        menuItemDTO.setItemId(String.valueOf(10L));
        exception = assertThrows(MenuException.class, () -> itemService.removeItemFromMenu(menuItemDTO));
        assertEquals(ITEM_NOT_EXIST_EXCEPTION, exception.getMessage());
    }

    @Test
    public void testFindAllItemsWithMenuName() {
        Pageable pageable = PageRequest.of(0, 2, Sort.by("id"));
        Page<Item> page = itemService.findAllItemsWithMenuName(MENU_WITH_FIVE_ITEMS, pageable);
        List<Item> items = page.get().toList();
        assertEquals(2, items.size());
        assertEquals(1L, items.get(0).getId());
        assertEquals(2L, items.get(1).getId());

        pageable = PageRequest.of(1, 2, Sort.by("id"));
        page = itemService.findAllItemsWithMenuName(MENU_WITH_FIVE_ITEMS, pageable);
        items = page.get().toList();
        assertEquals(2, items.size());
        assertEquals(3L, items.get(0).getId());
        assertEquals(4L, items.get(1).getId());

        pageable = PageRequest.of(2, 2, Sort.by("id"));
        page = itemService.findAllItemsWithMenuName(MENU_WITH_FIVE_ITEMS, pageable);
        items = page.get().toList();
        assertEquals(1, items.size());
        assertEquals(5L, items.get(0).getId());

        pageable = PageRequest.of(0, 3, Sort.by("id"));
        page = itemService.findAllItemsWithMenuName(MENU_WITH_FIVE_ITEMS, pageable);
        items = page.get().toList();
        assertEquals(3, items.size());
        assertEquals(3L, items.get(2).getId());

        pageable = PageRequest.of(0, 3, Sort.by("id"));
        page = itemService.findAllItemsWithMenuName(NON_EXISTING_MENU, pageable);
        items = page.get().toList();
        assertEquals(0, items.size());
    }

}
