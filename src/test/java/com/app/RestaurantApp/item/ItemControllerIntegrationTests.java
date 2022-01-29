package com.app.RestaurantApp.item;

import com.app.RestaurantApp.bonus.dto.BonusDTO;
import com.app.RestaurantApp.item.dto.ItemDTO;
import com.app.RestaurantApp.item.dto.ItemMenuDTO;
import com.app.RestaurantApp.item.dto.ItemPriceDTO;
import com.app.RestaurantApp.item.dto.MenuItemDTO;
import com.app.RestaurantApp.security.auth.JwtAuthenticationRequest;
import com.app.RestaurantApp.users.dto.UserTokenState;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.app.RestaurantApp.item.Constants.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ItemControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemService itemService;

    private final ObjectMapper mapper = new ObjectMapper();

    private static HttpHeaders headers;

    @BeforeAll
    static void setup(@Autowired TestRestTemplate testRestTemplate) {
        ResponseEntity<UserTokenState> responseEntity =
                testRestTemplate.postForEntity("/api/users/login", new JwtAuthenticationRequest(MANAGER_EMAIL, MANAGER_PWD),
                        UserTokenState.class);

        String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();
        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
    }

    @Test
    public void testGetItemById_ValidItem() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<ItemDTO> entity = restTemplate
                .exchange("/api/items/getItem/1",
                        HttpMethod.GET, httpEntity, ItemDTO.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertNotNull(entity.getBody());
        assertEquals(1L, Objects.requireNonNull(entity.getBody()).getId());
    }

    @Test
    public void testGetItemById_NullItem() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<ItemDTO> entity = restTemplate
                .exchange("/api/items/getItem/100",
                        HttpMethod.GET, httpEntity, ItemDTO.class);

        assertNull(entity.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());
    }

    @Test
    @Transactional
    public void testCreateUpdatePriceOnItem_Valid() throws Exception {
        ItemPriceDTO itemPriceDTO = new ItemPriceDTO();
        itemPriceDTO.setId(1L);
        itemPriceDTO.setNewPrice(200);

        mockMvc.perform(post("/api/items/createUpdatePriceOnItem")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(itemPriceDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Price successfully updated!"));
    }


    @Test
    @Transactional
    public void testCreateUpdatePriceOnItem_InvalidItem() throws Exception {
        ItemPriceDTO itemPriceDTO = new ItemPriceDTO();
        itemPriceDTO.setId(20L);
        itemPriceDTO.setNewPrice(200);

        mockMvc.perform(post("/api/items/createUpdatePriceOnItem")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(itemPriceDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Item does not exist!"));
    }

    @Test
    @Transactional
    public void testCreateUpdatePriceOnItem_InvalidPrice() throws Exception {
        ItemPriceDTO itemPriceDTO = new ItemPriceDTO();
        itemPriceDTO.setId(1L);
        itemPriceDTO.setNewPrice(-200);

        mockMvc.perform(post("/api/items/createUpdatePriceOnItem")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(itemPriceDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Price must be above 0!"));
    }

    @Test
    public void testGetPricesOfItem_Valid() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<ItemPriceDTO[]> entity = restTemplate
                .exchange("/api/items/getPricesOfItem/1",
                        HttpMethod.GET, httpEntity, ItemPriceDTO[].class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());

        List<ItemPriceDTO> prices = Arrays.stream(Objects.requireNonNull(entity.getBody())).toList();
        assertEquals(4, prices.size());
    }


    @Test
    public void testGetPricesOfItem_ItemException() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<ItemException> entity = restTemplate
                .exchange("/api/items/getPricesOfItem/100",
                        HttpMethod.GET, httpEntity, ItemException.class);

        assertNull(entity.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());
    }

    @Test
    @Transactional
    public void testRemoveItemFromMenu_Valid() throws Exception {
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setMenuName(VALID_MENU);
        menuItemDTO.setItemId(String.valueOf(VALID_ID));

        mockMvc.perform(post("/api/items/removeItemFromMenu")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(menuItemDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Item is succesfully removed from menu!"));
    }

    @Test
    @Transactional
    public void testRemoveItemFromMenu_InvalidMenu() throws Exception {
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setMenuName(NON_EXISTING_MENU);
        menuItemDTO.setItemId(String.valueOf(VALID_ID));

        mockMvc.perform(post("/api/items/removeItemFromMenu")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(menuItemDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(MENU_EXCEPTION));
    }

    @Test
    @Transactional
    public void testRemoveItemFromMenu_InvalidItem() throws Exception {
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setMenuName(VALID_MENU);
        menuItemDTO.setItemId(String.valueOf(INVALID_ID));

        mockMvc.perform(post("/api/items/removeItemFromMenu")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(menuItemDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(ITEM_EXCEPTION));
    }

    @Test
    @Transactional
    public void testRemoveItemFromMenu_NotExistItemProblem() throws Exception {
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setMenuName(VALID_MENU);
        menuItemDTO.setItemId("7");

        mockMvc.perform(post("/api/items/removeItemFromMenu")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(menuItemDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(ITEM_NOT_EXIST_EXCEPTION));
    }

    @Test
    @Transactional
    public void testAddItemToMenu_Valid() throws Exception {
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setMenuName(VALID_MENU);
        menuItemDTO.setItemId("7");

        mockMvc.perform(post("/api/items/addItemToMenu")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(menuItemDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Item is succesfully added to menu!"));
    }

    @Test
    @Transactional
    public void testAddItemToMenu_InvalidMenu() throws Exception {
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setMenuName(NON_EXISTING_MENU);
        menuItemDTO.setItemId(String.valueOf(VALID_ID));

        mockMvc.perform(post("/api/items/addItemToMenu")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(menuItemDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(MENU_EXCEPTION));
    }

    @Test
    @Transactional
    public void testAddItemToMenu_InvalidItem() throws Exception {
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setMenuName(VALID_MENU);
        menuItemDTO.setItemId(String.valueOf(INVALID_ID));

        mockMvc.perform(post("/api/items/addItemToMenu")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(menuItemDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(ITEM_EXCEPTION));
    }

    @Test
    @Transactional
    public void testAddItemToMenu_ExistItemProblem() throws Exception {
        MenuItemDTO menuItemDTO = new MenuItemDTO();
        menuItemDTO.setMenuName(VALID_MENU);
        menuItemDTO.setItemId(String.valueOf(VALID_ID));

        mockMvc.perform(post("/api/items/addItemToMenu")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(menuItemDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(ITEM_EXISTS_EXCEPTION));
    }

    // /findAllItemsWithMenuName?name=Specijalni%20meni&page=0&size=3&sort=id
    @Test
    public void testFindAllItemsWithMenuName() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<ItemMenuDTO[]> entity = restTemplate
                .exchange(String.format
                                ("/api/items/findAllItemsWithMenuName?name=%s&page=%s&size=%s&sort=id",
                                        VALID_MENU, 0, 3),
                        HttpMethod.GET, httpEntity, ItemMenuDTO[].class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());

        List<ItemMenuDTO> items = Arrays.stream(Objects.requireNonNull(entity.getBody())).toList();
        assertNotNull(items);
        assertEquals(3, items.size());
        assertEquals(1L, items.get(0).getId());
        assertEquals(2L, items.get(1).getId());
        assertEquals(3L, items.get(2).getId());

        entity = restTemplate
                .exchange(String.format
                                ("/api/items/findAllItemsWithMenuName?name=%s&page=%s&size=%s&sort=id",
                                        VALID_MENU, 1, 3),
                        HttpMethod.GET, httpEntity, ItemMenuDTO[].class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());

        items = Arrays.stream(Objects.requireNonNull(entity.getBody())).toList();
        assertNotNull(items);
        assertEquals(2, items.size());
        assertEquals(4L, items.get(0).getId());
        assertEquals(5L, items.get(1).getId());
    }
}
