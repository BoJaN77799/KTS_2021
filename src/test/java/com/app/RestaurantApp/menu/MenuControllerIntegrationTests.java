package com.app.RestaurantApp.menu;

import com.app.RestaurantApp.item.dto.ItemDTO;
import com.app.RestaurantApp.item.dto.MenuItemDTO;
import com.app.RestaurantApp.security.auth.JwtAuthenticationRequest;
import com.app.RestaurantApp.users.dto.UserTokenState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
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

import static com.app.RestaurantApp.menu.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MenuControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MenuService menuService;

    private final ObjectMapper mapper = new ObjectMapper();

    private static HttpHeaders headers;

//    @BeforeAll
//    static void setup(@Autowired TestRestTemplate testRestTemplate) {
//        ResponseEntity<UserTokenState> responseEntity =
//                testRestTemplate.postForEntity("/api/users/login", new JwtAuthenticationRequest(MANAGER_EMAIL, MANAGER_PWD),
//                        UserTokenState.class);
//
//        String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();
//        headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + accessToken);
//    }
//
//    @Test
//    @Transactional
//    public void testCreateUpdateMenu_ValidCreating() throws Exception {
//        mockMvc.perform(post("/api/menus/createUpdateMenu")
//                .headers(headers)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(NON_EXISTING_NAME))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$").value("Menu created successfully!"));
//    }
//
//    @Test
//    @Transactional
//    public void testCreateUpdateMenu_ValidUpdating() throws Exception {
//        mockMvc.perform(post("/api/menus/createUpdateMenu")
//                .headers(headers)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(EXISTING_NAME))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").value("Menu updated successfully!"));
//    }
//
//
//    @Test
//    public void testGetItemsOfMenu_Valid() {
//        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
//
//        ResponseEntity<ItemDTO[]> entity = restTemplate
//                .exchange(String.format("/api/menus/getItemsOfMenu/%s", EXISTING_NAME),
//                        HttpMethod.GET, httpEntity, ItemDTO[].class);
//        assertEquals(HttpStatus.OK, entity.getStatusCode());
//
//        List<ItemDTO> items = Arrays.stream(Objects.requireNonNull(entity.getBody())).toList();
//        assertEquals(2, items.size());
//    }
//
//    @Test
//    public void testGetItemsOfMenu_NotFound() {
//        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
//
//        ResponseEntity<ItemDTO[]> entity = restTemplate
//                .exchange(String.format("/api/menus/getItemsOfMenu/%s", EMPTY_MENU),
//                        HttpMethod.GET, httpEntity, ItemDTO[].class);
//        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
//        assertNotNull(entity.getBody());
//
//        List<ItemDTO> items = Arrays.stream(Objects.requireNonNull(entity.getBody())).toList();
//        assertEquals(0, items.size());
//    }
//
//    @Test
//    public void testGetItemsOfMenu_MenuException() {
//        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
//
//        ResponseEntity<MenuException> entity = restTemplate
//                .exchange(String.format("/api/menus/getItemsOfMenu/%s", NON_EXISTING_NAME),
//                        HttpMethod.GET, httpEntity, MenuException.class);
//        assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());
//        assertNull(entity.getBody());
//    }
//
//    @Test
//    @Transactional
//    public void testRemoveItemFromMenu_Valid() throws Exception {
//        MenuItemDTO menuItemDTO = new MenuItemDTO();
//        menuItemDTO.setMenuName(EXISTING_NAME);
//        menuItemDTO.setItemId(EXISTING_ITEM_ID);
//
//        mockMvc.perform(post("/api/menus/removeItemFromMenu")
//                .headers(headers)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString(menuItemDTO)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").value("Item is succesfully removed from menu!"));
//    }
//
//    @Test
//    @Transactional
//    public void testRemoveItemFromMenu_MenuException() throws Exception {
//        MenuItemDTO menuItemDTO = new MenuItemDTO();
//        menuItemDTO.setMenuName(NON_EXISTING_NAME);
//        menuItemDTO.setItemId(EXISTING_ITEM_ID);
//
//        mockMvc.perform(post("/api/menus/removeItemFromMenu")
//                .headers(headers)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString(menuItemDTO)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$").value("Menu does not exist!"));
//    }
//
//    @Test
//    @Transactional
//    public void testRemoveItemFromMenu_ItemException() throws Exception {
//        MenuItemDTO menuItemDTO = new MenuItemDTO();
//        menuItemDTO.setMenuName(EXISTING_NAME);
//        menuItemDTO.setItemId(NON_EXISTING_ITEM_ID);
//
//        mockMvc.perform(post("/api/menus/removeItemFromMenu")
//                .headers(headers)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString(menuItemDTO)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$").value("Item does not exist!"));
//    }
//
//    @Test
//    @Transactional
//    public void testRemoveItemFromMenu_ItemNotInMenu() throws Exception {
//        MenuItemDTO menuItemDTO = new MenuItemDTO();
//        menuItemDTO.setMenuName(EXISTING_NAME);
//        menuItemDTO.setItemId("3");
//
//        mockMvc.perform(post("/api/menus/removeItemFromMenu")
//                .headers(headers)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString(menuItemDTO)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$").value("Item does not exist in menu!"));
//    }
//
//    @Test
//    @Transactional
//    public void testAddItemToMenu_Valid() throws Exception {
//        MenuItemDTO menuItemDTO = new MenuItemDTO();
//        menuItemDTO.setMenuName(EXISTING_NAME);
//        menuItemDTO.setItemId("3");
//
//        mockMvc.perform(post("/api/menus/addItemToMenu")
//                .headers(headers)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString(menuItemDTO)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").value("Item is succesfully added to menu!"));
//    }
//
//    @Test
//    @Transactional
//    public void testAddItemToMenu_MenuException() throws Exception {
//        MenuItemDTO menuItemDTO = new MenuItemDTO();
//        menuItemDTO.setMenuName(NON_EXISTING_NAME);
//        menuItemDTO.setItemId(EXISTING_ITEM_ID);
//
//        mockMvc.perform(post("/api/menus/addItemToMenu")
//                .headers(headers)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString(menuItemDTO)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$").value("Menu does not exist!"));
//    }
//
//    @Test
//    @Transactional
//    public void testAddItemToMenu_ItemException() throws Exception {
//        MenuItemDTO menuItemDTO = new MenuItemDTO();
//        menuItemDTO.setMenuName(EXISTING_NAME);
//        menuItemDTO.setItemId(NON_EXISTING_ITEM_ID);
//
//        mockMvc.perform(post("/api/menus/addItemToMenu")
//                .headers(headers)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString(menuItemDTO)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$").value("Item does not exist!"));
//    }
//
//    @Test
//    @Transactional
//    public void testAddItemToMenu_ItemInMenu() throws Exception {
//        MenuItemDTO menuItemDTO = new MenuItemDTO();
//        menuItemDTO.setMenuName(EXISTING_NAME);
//        menuItemDTO.setItemId(EXISTING_ITEM_ID);
//
//        mockMvc.perform(post("/api/menus/addItemToMenu")
//                .headers(headers)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString(menuItemDTO)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$").value("Item already exists in menu!"));
//    }
}
