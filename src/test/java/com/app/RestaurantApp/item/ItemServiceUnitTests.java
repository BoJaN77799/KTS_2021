package com.app.RestaurantApp.item;

import com.app.RestaurantApp.item.dto.ItemPriceDTO;
import com.app.RestaurantApp.item.dto.MenuItemDTO;
import com.app.RestaurantApp.menu.Menu;
import com.app.RestaurantApp.menu.MenuException;
import com.app.RestaurantApp.menu.MenuService;
import com.app.RestaurantApp.price.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.app.RestaurantApp.item.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ItemServiceUnitTests {

    @Autowired
    private ItemService itemService;

    @MockBean
    private ItemRepository itemRepositoryMock;

    @MockBean
    private MenuService menuServiceMock;

    private MenuItemDTO menuItemDTO;

    @BeforeEach
    public void setup() {
        Item i = createItem();

        given(itemRepositoryMock.findByIdItemWithPrices(VALID_ID)).willReturn(i);
        given(itemRepositoryMock.save(any())).willReturn(i);
        given(itemRepositoryMock.findById(VALID_ID)).willReturn(java.util.Optional.of(i));

        Menu m = createMenu();
        given(menuServiceMock.findByName(VALID_MENU)).willReturn(m);

        menuItemDTO = new MenuItemDTO();
        menuItemDTO.setItemId(String.valueOf(VALID_ID));
        menuItemDTO.setMenuName(VALID_MENU);
    }

    @Test
    public void testCreateUpdatePriceOnItem_ValidCreating() throws ItemException {
        ItemPriceDTO itemPriceDTO = new ItemPriceDTO();
        itemPriceDTO.setId(VALID_ID);
        itemPriceDTO.setNewPrice(100);

        assertTrue(itemService.createUpdatePriceOnItem(itemPriceDTO));

        verify(itemRepositoryMock, times(1)).findByIdItemWithPrices(VALID_ID);
        verify(itemRepositoryMock, times(1)).save(any());
    }

    @Test
    public void testCreateUpdatePriceOnItem_ValidUpdating() throws ItemException {
        Item i = createItem();
        i.setCurrentPrice(10.0);
        given(itemRepositoryMock.findByIdItemWithPrices(VALID_ID)).willReturn(i);

        ItemPriceDTO itemPriceDTO = new ItemPriceDTO();
        itemPriceDTO.setId(VALID_ID);
        itemPriceDTO.setNewPrice(100);

        assertFalse(itemService.createUpdatePriceOnItem(itemPriceDTO));

        verify(itemRepositoryMock, times(1)).findByIdItemWithPrices(VALID_ID);
        verify(itemRepositoryMock, times(1)).save(any());
    }

    @Test
    public void testCreateUpdatePriceOnItem_InvalidItem() {
        given(itemRepositoryMock.findByIdItemWithPrices(any())).willReturn(null);

        Exception exception = assertThrows(ItemException.class, () -> {
            ItemPriceDTO itemPriceDTO = new ItemPriceDTO();
            itemPriceDTO.setId(VALID_ID);
            itemPriceDTO.setNewPrice(100);

            itemService.createUpdatePriceOnItem(itemPriceDTO);
        });

        assertEquals(ITEM_EXCEPTION, exception.getMessage());
    }

    @Test
    public void testCreateUpdatePriceOnItem_InvalidPrice() {
        Item i = createItem();
        i.setCurrentPrice(-10.0);
        given(itemRepositoryMock.findByIdItemWithPrices(VALID_ID)).willReturn(i);

        Exception exception = assertThrows(ItemException.class, () -> {
            ItemPriceDTO itemPriceDTO = new ItemPriceDTO();
            itemPriceDTO.setId(VALID_ID);
            itemPriceDTO.setNewPrice(-100);

            itemService.createUpdatePriceOnItem(itemPriceDTO);
        });

        assertEquals(PRICE_EXCEPTION, exception.getMessage());
    }

    @Test
    public void testGetPricesOfItem_Valid() throws ItemException {
        List<ItemPriceDTO> items = itemService.getPricesOfItem("1");

        assertEquals(3, items.size());
        assertEquals("08.12.2021.", items.get(0).getDateFrom());
        assertEquals("09.12.2021.", items.get(1).getDateFrom());
        assertEquals("10.12.2021.", items.get(2).getDateFrom());

        verify(itemRepositoryMock, times(1)).findByIdItemWithPrices(1L);
    }

    @Test
    public void testGetPricesOfItem_InvalidItem() {
        given(itemRepositoryMock.findByIdItemWithPrices(VALID_ID)).willReturn(null);

        Exception exception = assertThrows(ItemException.class, () -> {
            itemService.getPricesOfItem("1");
        });

        assertEquals(ITEM_EXCEPTION, exception.getMessage());
    }

    @Test
    public void testAddItemToMenu_ValidAdding() throws MenuException, ItemException {
        itemService.addItemToMenu(menuItemDTO);

        verify(menuServiceMock, times(1)).findByName(VALID_MENU);
        verify(itemRepositoryMock, times(1)).findById(VALID_ID);
        verify(itemRepositoryMock, times(1)).save(any());
    }

    @Test
    public void testAddItemToMenu_InvalidMenu() {
        given(menuServiceMock.findByName(VALID_MENU)).willReturn(null);

        Exception exception = assertThrows(MenuException.class, () -> itemService.addItemToMenu(menuItemDTO));

        assertEquals(MENU_EXCEPTION, exception.getMessage());
    }

    @Test
    public void testAddItemToMenu_InvalidItem() {
        given(itemRepositoryMock.findById(VALID_ID)).willReturn(Optional.empty());

        Exception exception = assertThrows(ItemException.class, () -> itemService.addItemToMenu(menuItemDTO));

        assertEquals(ITEM_EXCEPTION, exception.getMessage());
    }

    @Test
    public void testAddItemToMenu_ItemExistsProblem() {
        Item i = new Item();
        i.setId(VALID_ID);
        i.setMenu(VALID_MENU);

        given(itemRepositoryMock.findById(VALID_ID)).willReturn(java.util.Optional.of(i));

        Exception exception = assertThrows(MenuException.class, () -> itemService.addItemToMenu(menuItemDTO));

        assertEquals(ITEM_EXISTS_EXCEPTION, exception.getMessage());

    }

    @Test
    public void testRemoveItemFromMenu_ValidRemoving() throws MenuException, ItemException {
        Item i = new Item();
        i.setId(VALID_ID);
        i.setMenu(VALID_MENU);

        given(itemRepositoryMock.findById(VALID_ID)).willReturn(java.util.Optional.of(i));

        itemService.removeItemFromMenu(menuItemDTO);

        verify(menuServiceMock, times(1)).findByName(VALID_MENU);
        verify(itemRepositoryMock, times(1)).findById(VALID_ID);
        verify(itemRepositoryMock, times(1)).save(any());
    }

    @Test
    public void testRemoveItemFromMenu_InvalidMenu() {
        given(menuServiceMock.findByName(VALID_MENU)).willReturn(null);

        Exception exception = assertThrows(MenuException.class, () -> itemService.removeItemFromMenu(menuItemDTO));

        assertEquals(MENU_EXCEPTION, exception.getMessage());
    }

    @Test
    public void testRemoveItemFromMenu_InvalidItem() {
        given(itemRepositoryMock.findById(VALID_ID)).willReturn(Optional.empty());

        Exception exception = assertThrows(ItemException.class, () -> itemService.removeItemFromMenu(menuItemDTO));

        assertEquals(ITEM_EXCEPTION, exception.getMessage());
    }

    @Test
    public void testRemoveItemFromMenu_ItemNotExistProblem() {
        Exception exception = assertThrows(MenuException.class, () -> itemService.removeItemFromMenu(menuItemDTO));

        assertEquals(ITEM_NOT_EXIST_EXCEPTION, exception.getMessage());
    }

    private Item createItem() {
        Item i = new Item();
        i.setId(VALID_ID);
        i.setPrices(new HashSet<>());
        i.setCurrentPrice(0.0);

        Price p1 = new Price(100, 1639090800000L, i);
        Price p2 = new Price(200, 1639004400000L, i);
        Price p3 = new Price(300, 1638918000000L, i);

        i.getPrices().add(p1);
        i.getPrices().add(p2);
        i.getPrices().add(p3);

        i.setMenu(NON_EXISTING_MENU);
        return i;
    }

    private Menu createMenu() {
        return new Menu(VALID_MENU);
    }
}
