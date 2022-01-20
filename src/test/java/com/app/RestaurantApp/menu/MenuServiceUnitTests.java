package com.app.RestaurantApp.menu;

import com.app.RestaurantApp.item.Item;
import com.app.RestaurantApp.item.ItemException;
import com.app.RestaurantApp.item.ItemService;
import com.app.RestaurantApp.item.dto.ItemDTO;
import com.app.RestaurantApp.item.dto.MenuItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import static com.app.RestaurantApp.menu.Constants.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MenuServiceUnitTests {

    @Autowired
    private MenuService menuService;

    @MockBean
    private MenuRepository menuRepositoryMock;

    @BeforeEach
    public void setup() {
        Menu m = createMenu();
        given(menuRepositoryMock.findByName(VALID_NAME)).willReturn(m);

        List<Menu> menus = createCollectionOfMenus();
        given(menuRepositoryMock.findAllWithSpecificStatus(anyBoolean())).willReturn(menus);
    }

    @Test
    public void testCreateMenu_ValidCreating() throws MenuException {
        given(menuRepositoryMock.findByName(VALID_NAME)).willReturn(null);

        menuService.createMenu(VALID_NAME);

        verify(menuRepositoryMock, times(1)).findByName(VALID_NAME);
        verify(menuRepositoryMock, times(1)).save(any());
    }

    @Test
    public void testCreateMenu_InvalidMenu() throws MenuException {
        Exception exception = assertThrows(MenuException.class, () -> menuService.createMenu(VALID_NAME));

        assertEquals(EXISTING_MENU_EXCEPTION, exception.getMessage());
    }

    @Test
    public void testUpdateMenu_ValidUpdating() throws MenuException {
        menuService.updateMenu(VALID_NAME);

        verify(menuRepositoryMock, times(1)).findByName(VALID_NAME);
        verify(menuRepositoryMock, times(1)).save(any());
    }

    @Test
    public void testUpdateMenu_InvalidUpdating() {
        given(menuRepositoryMock.findByName(VALID_NAME)).willReturn(null);

        Exception exception = assertThrows(MenuException.class, () -> menuService.updateMenu(VALID_NAME));

        assertEquals(NON_EXISTING_MENU_EXCEPTION, exception.getMessage());
    }

    @Test
    public void testFindAllActiveMenuNames_Valid() {
        String[] namesOfMenus = new String[] { FIRST_ACTIVE_MENU, SECOND_ACTIVE_MENU, UNDEFINED_NAME_MENU };
        List<String>  menusNames = menuService.findAllActiveMenuNames();

        boolean indicator = true;
        for (String name : namesOfMenus) {
            if (!menusNames.contains(name)) {
                indicator = false;
                break;
            }
        }
        assertTrue(indicator);
        assertEquals(3, menusNames.size());
        verify(menuRepositoryMock, times(1)).findAllWithSpecificStatus(anyBoolean());

        given(menuRepositoryMock.findAllWithSpecificStatus(anyBoolean())).willReturn(new ArrayList<>());
        menusNames = menuService.findAllActiveMenuNames();
        assertEquals(1, menusNames.size());
        assertEquals(UNDEFINED_NAME_MENU, menusNames.get(0));
        verify(menuRepositoryMock, times(2)).findAllWithSpecificStatus(anyBoolean());
    }

    private Menu createMenu() {
        Menu m = new Menu();
        m.setName(VALID_NAME);
        m.setActiveMenu(true);

        return m;
    }

    private List<Menu> createCollectionOfMenus () {
        List<Menu> menus = new ArrayList<>();

        Menu firstMenu = new Menu(FIRST_ACTIVE_MENU);
        menus.add(firstMenu);

        Menu secondMenu = new Menu(SECOND_ACTIVE_MENU);
        menus.add(secondMenu);

        return menus;
    }

}
