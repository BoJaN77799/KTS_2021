package com.app.RestaurantApp.menu;

import com.app.RestaurantApp.item.ItemException;
import com.app.RestaurantApp.item.ItemService;
import com.app.RestaurantApp.item.dto.ItemDTO;
import com.app.RestaurantApp.item.dto.MenuItemDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.List;

import static com.app.RestaurantApp.menu.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MenuServiceIntegrationTests {

//    @Autowired
//    private MenuService menuService;
//
//    @Autowired
//    private MenuRepository menuRepository;
//
//    @Autowired
//    private ItemService itemService;
//
//    @Test
//    @Transactional
//    public void testCreateUpdateMenu() throws MenuException {
//        int menuCount = menuRepository.findAll().size();
//
//        assertFalse(menuService.createUpdateMenu(VALID_NAME));
//        assertEquals(menuCount, menuRepository.findAll().size());
//
//        assertTrue(menuService.createUpdateMenu(INVALID_NAME));
//        assertEquals(menuCount + 1, menuRepository.findAll().size());
//    }
//
//    @Test
//    @Transactional
//    public void testGetItemsOfMenu() throws MenuException {
//         List<ItemDTO> items = menuService.getItemsOfMenu(MENU_WITH_ITEMS);
//         assertEquals(2, items.size());
//
//         items = menuService.getItemsOfMenu(MENU_WITHOUT_ITEMS);
//         assertEquals(0, items.size());
//    }
//
//    @Test
//    @Transactional
//    public void testRemoveItemFromMenu() throws MenuException, ItemException {
//        MenuItemDTO menuItemDTO = new MenuItemDTO();
//        menuItemDTO.setMenuName(MENU_WITH_ITEMS);
//        menuItemDTO.setItemId("1");
//
//        List<ItemDTO> items = menuService.getItemsOfMenu(MENU_WITH_ITEMS);
//        assertEquals(2, items.size());
//
//        menuService.removeItemFromMenu(menuItemDTO);
//        items = menuService.getItemsOfMenu(MENU_WITH_ITEMS);
//        assertEquals(1, items.size());
//    }
//
//    @Test
//    @Transactional
//    public void testAddItemToMenu() throws MenuException, ItemException {
//        MenuItemDTO menuItemDTO = new MenuItemDTO();
//        menuItemDTO.setMenuName(MENU_WITH_ITEMS);
//        menuItemDTO.setItemId("3");
//
//        menuService.addItemToMenu(menuItemDTO);
//        assertEquals(3, menuService.getItemsOfMenu(MENU_WITH_ITEMS).size());
//    }

}
