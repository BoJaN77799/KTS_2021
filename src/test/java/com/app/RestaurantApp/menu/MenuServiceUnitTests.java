package com.app.RestaurantApp.menu;

import com.app.RestaurantApp.item.Item;
import com.app.RestaurantApp.item.ItemException;
import com.app.RestaurantApp.item.ItemService;
import com.app.RestaurantApp.item.dto.ItemDTO;
import com.app.RestaurantApp.menu.dto.MenuItemDTO;
import com.app.RestaurantApp.users.UserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.List;

import static com.app.RestaurantApp.bonus.Constants.EMAIL;
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

    @MockBean
    private ItemService itemServiceMock;

    @BeforeEach
    public void setup() {
        Menu m = createMenu();

        given(menuRepositoryMock.findByName(MENU_NAME)).willReturn(m);
        given(menuRepositoryMock.findByNameWithItems(MENU_NAME)).willReturn(m);
    }

    @Test
    public void testCreateUpdateMenu_ValidCreating() throws MenuException {
        Menu menuMocked = new Menu(MENU_NAME);
        given(menuRepositoryMock.findByName(any())).willReturn(null);
        given(menuRepositoryMock.save(any())).willReturn(menuMocked);

        assertTrue(menuService.createUpdateMenu(MENU_NAME));

        verify(menuRepositoryMock, times(1)).findByName(MENU_NAME);
        verify(menuRepositoryMock, times(1)).save(any());
    }

    @Test
    public void testCreateUpdateMenu_ValidUpdating() throws MenuException {
        Menu menuMocked = new Menu(MENU_NAME);
        given(menuRepositoryMock.save(any())).willReturn(menuMocked);

        assertFalse(menuService.createUpdateMenu(MENU_NAME));

        verify(menuRepositoryMock, times(1)).findByName(MENU_NAME);
        verify(menuRepositoryMock, times(1)).save(any());
    }

    @Test
    public void testGetItemsOfMenu_ValidMenu() throws MenuException {
        List<ItemDTO> items = menuService.getItemsOfMenu(MENU_NAME);
        assertEquals(3, items.size());

        verify(menuRepositoryMock, times(1)).findByNameWithItems(MENU_NAME);
    }

    @Test
    public void testGetItemsOfMenu_InvalidMenu() {
        given(menuRepositoryMock.findByNameWithItems(any())).willReturn(null);

        Exception exception = assertThrows(MenuException.class, () -> menuService.getItemsOfMenu(MENU_NAME));

        assertEquals(MENU_EXCEPTION, exception.getMessage());
    }

    @Test
    public void testGetItemsOfMenu_EmptyMenu() throws MenuException {
        Menu emptyMenu = new Menu(MENU_NAME);
        given(menuRepositoryMock.findByNameWithItems(MENU_NAME)).willReturn(emptyMenu);

        List<ItemDTO> items = menuService.getItemsOfMenu(MENU_NAME);
        assertEquals(0, items.size());

        verify(menuRepositoryMock, times(1)).findByNameWithItems(MENU_NAME);
    }

    @Test
    public void testRemoveItemFromMenu_Valid() throws MenuException, ItemException {
        Menu m = createMenu();
        Item i4 = createItem();
        m.getItems().add(i4);

        given(menuRepositoryMock.findByNameWithItems(MENU_NAME)).willReturn(m);
        given(itemServiceMock.findItemById(FOURTH_ID)).willReturn(i4);

        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setItemId(FOURTH_ID.toString());
        menuItemDTO.setMenuName(MENU_NAME);

        menuService.removeItemFromMenu(menuItemDTO);

        verify(menuRepositoryMock, times(1)).findByNameWithItems(MENU_NAME);
        verify(itemServiceMock, times(1)).findItemById(FOURTH_ID);

        assertEquals(3, m.getItems().size());

    }

    @Test
    public void testRemoveItemFromMenu_InvalidMenu() {
        given(menuRepositoryMock.findByNameWithItems(any())).willReturn(null);

        Exception exception = assertThrows(MenuException.class, () ->
        {
            MenuItemDTO menuItemDTO = new MenuItemDTO();
            menuItemDTO.setItemId(FOURTH_ID.toString());
            menuItemDTO.setMenuName(MENU_NAME);

            menuService.removeItemFromMenu(menuItemDTO);
        });

        assertEquals(MENU_EXCEPTION, exception.getMessage());
    }

    @Test
    public void testRemoveItemFromMenu_InvalidItem() {
        given(itemServiceMock.findItemById(any())).willReturn(null);

        Exception exception = assertThrows(ItemException.class, () -> {
            MenuItemDTO menuItemDTO = new MenuItemDTO();
            menuItemDTO.setItemId(FOURTH_ID.toString());
            menuItemDTO.setMenuName(MENU_NAME);

            menuService.removeItemFromMenu(menuItemDTO);
        });

        assertEquals(ITEM_EXCEPTION, exception.getMessage());
    }

    @Test
    public void testRemoveItemFromMenu_NotExistingItem() {
        Menu m = createMenu();
        Item i4 = createItem();

        given(menuRepositoryMock.findByNameWithItems(MENU_NAME)).willReturn(m);
        given(itemServiceMock.findItemById(FOURTH_ID)).willReturn(i4);

        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setItemId(FOURTH_ID.toString());
        menuItemDTO.setMenuName(MENU_NAME);

        Exception exception = assertThrows(MenuException.class, () -> {
            menuService.removeItemFromMenu(menuItemDTO);
        });

        assertEquals(NON_EXISTING_ITEM, exception.getMessage());
    }

    @Test
    public void testAddItemToMenu_Valid() throws MenuException, ItemException {
        Menu m = createMenu();
        Item i4 = createItem();

        given(menuRepositoryMock.findByNameWithItems(MENU_NAME)).willReturn(m);
        given(itemServiceMock.findItemById(FOURTH_ID)).willReturn(i4);

        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setItemId(FOURTH_ID.toString());
        menuItemDTO.setMenuName(MENU_NAME);

        menuService.addItemToMenu(menuItemDTO);

        verify(menuRepositoryMock, times(1)).findByNameWithItems(MENU_NAME);
        verify(itemServiceMock, times(1)).findItemById(FOURTH_ID);

        assertEquals(4, m.getItems().size());
    }

    @Test
    public void testAddItemToMenu_InvalidMenu() {
        given(menuRepositoryMock.findByNameWithItems(any())).willReturn(null);

        Exception exception = assertThrows(MenuException.class, () ->
        {
            MenuItemDTO menuItemDTO = new MenuItemDTO();
            menuItemDTO.setItemId(FOURTH_ID.toString());
            menuItemDTO.setMenuName(MENU_NAME);

            menuService.addItemToMenu(menuItemDTO);
        });

        assertEquals(MENU_EXCEPTION, exception.getMessage());
    }

    @Test
    public void testAddItemToMenu_InvalidItem() {
        given(itemServiceMock.findItemById(any())).willReturn(null);

        Exception exception = assertThrows(ItemException.class, () -> {
            MenuItemDTO menuItemDTO = new MenuItemDTO();
            menuItemDTO.setItemId(FOURTH_ID.toString());
            menuItemDTO.setMenuName(MENU_NAME);

            menuService.addItemToMenu(menuItemDTO);
        });

        assertEquals(ITEM_EXCEPTION, exception.getMessage());
    }

    @Test
    public void testAddItemToMenu_ExistingItem() {
        Menu m = createMenu();
        Item i4 = createItem();
        m.getItems().add(i4);

        given(menuRepositoryMock.findByNameWithItems(MENU_NAME)).willReturn(m);
        given(itemServiceMock.findItemById(FOURTH_ID)).willReturn(i4);

        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setItemId(FOURTH_ID.toString());
        menuItemDTO.setMenuName(MENU_NAME);

        Exception exception = assertThrows(MenuException.class, () -> {
            menuService.addItemToMenu(menuItemDTO);
        });

        assertEquals(EXISTING_ITEM, exception.getMessage());
    }

    private Menu createMenu() {
        Menu m = new Menu(MENU_NAME);
        m.setItems(new HashSet<>());

        Item i1 = new Item();
        i1.setId(FIRST_ID);
        i1.setName(FIRST_ITEM);
        i1.setCurrentPrice(100.0);

        Item i2 = new Item();
        i2.setId(SECOND_ID);
        i2.setName(SECOND_ITEM);
        i2.setCurrentPrice(150.0);

        Item i3 = new Item();
        i3.setId(THIRD_ID);
        i3.setName(THIRD_ITEM);
        i3.setCurrentPrice(200.0);

        m.getItems().add(i1);
        m.getItems().add(i2);
        m.getItems().add(i3);

        return m;
    }

    private Item createItem() {
        Item i4 = new Item();
        i4.setId(FOURTH_ID);
        i4.setName(FOURTH_ITEM);
        i4.setCurrentPrice(300.0);
        return i4;
    }
}
