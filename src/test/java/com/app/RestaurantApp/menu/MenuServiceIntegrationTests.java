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

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRepository menuRepository;

    @Test
    public void testFindByName() {
        Menu m = menuService.findByName(VALID_NAME);
        assertNotNull(m);
        assertEquals(VALID_NAME, m.getName());
        assertTrue(m.isActiveMenu());

        m = menuService.findByName(INVALID_NAME);
        assertNull(m);
    }

    @Test
    @Transactional
    public void testCreateMenu() throws MenuException {
        int menuCount = menuRepository.findAll().size();
        assertEquals(4, menuCount);

        menuService.createMenu(VALID_CREATING_NAME);
        assertEquals(5, menuCount + 1);
        Menu m = menuRepository.findByName(VALID_CREATING_NAME);
        assertNotNull(m);
        assertEquals(VALID_CREATING_NAME, m.getName());
        assertTrue(m.isActiveMenu());

        Exception exception = assertThrows(MenuException.class, () ->
                menuService.createMenu(INVALID_CREATING_NAME));
        assertEquals(5, menuCount + 1);
        assertEquals(EXISTING_MENU_EXCEPTION, exception.getMessage());
    }

    @Test
    @Transactional
    public void testUpdateMenu() throws MenuException {
        Menu m = menuRepository.findByName(VALID_NAME);
        assertTrue(m.isActiveMenu());

        menuService.updateMenu(VALID_NAME);
        m = menuRepository.findByName(VALID_NAME);
        assertFalse(m.isActiveMenu());

        Exception exception = assertThrows(MenuException.class, () ->
                menuService.updateMenu(INVALID_UPDATING_NAME));
        assertEquals(NON_EXISTING_MENU_EXCEPTION, exception.getMessage());
    }

    @Test
    @Transactional
    public void testFindAllWithSpecificStatus() {
        List<Menu> menus = menuService.findAllWithSpecificStatus(true);
        assertEquals(2, menus.size());

        menus = menuService.findAllWithSpecificStatus(false);
        assertEquals(2, menus.size());
    }

    @Test
    @Transactional
    public void testFindAllActiveMenuNames() {
        List<String> names = menuService.findAllActiveMenuNames();
        assertEquals(3, names.size());
    }

}
