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
    @Transactional
    public void testCreateMenu_ValidCreating() throws Exception {
        mockMvc.perform(post("/api/menus/createMenu")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(VALID_CREATING_NAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Menu created successfully!"));
    }

    @Test
    @Transactional
    public void testCreateMenu_InvalidMenu() throws Exception {
        mockMvc.perform(post("/api/menus/createMenu")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(INVALID_CREATING_NAME))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(EXISTING_MENU_EXCEPTION));
    }


    @Test
    @Transactional
    public void testUpdateMenu_ValidUpdating() throws Exception {
        mockMvc.perform(post("/api/menus/updateMenu")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(VALID_UPDATING_NAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Menu updated successfully!"));
    }

    @Test
    @Transactional
    public void testUpdateMenu_InvalidMenu() throws Exception {
        mockMvc.perform(post("/api/menus/updateMenu")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(INVALID_UPDATING_NAME))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(NON_EXISTING_MENU_EXCEPTION));
    }

    @Test
    public void testFindAllWithSpecificStatus_Finding_ActiveMenus() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<Menu[]> entity = restTemplate
                .exchange(String.format("/api/menus/findAllWithSpecificStatus/%s", true),
                        HttpMethod.GET, httpEntity, Menu[].class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());

        List<Menu> activeMenus = Arrays.stream(Objects.requireNonNull(entity.getBody())).toList();
        assertEquals(2, activeMenus.size());
    }

    @Test
    public void testFindAllWithSpecificStatus_Finding_InactiveMenus() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<Menu[]> entity = restTemplate
                .exchange(String.format("/api/menus/findAllWithSpecificStatus/%s", false),
                        HttpMethod.GET, httpEntity, Menu[].class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());

        List<Menu> inactiveMenus = Arrays.stream(Objects.requireNonNull(entity.getBody())).toList();
        assertEquals(2, inactiveMenus.size());
    }

    @Test
    public void testFindAllActiveMenuNames() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<String[]> entity = restTemplate
                .exchange("/api/menus/findAllActiveMenuNames",
                        HttpMethod.GET, httpEntity, String[].class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());

        List<String> names = Arrays.stream(Objects.requireNonNull(entity.getBody())).toList();
        assertEquals(3, names.size());
    }
}
