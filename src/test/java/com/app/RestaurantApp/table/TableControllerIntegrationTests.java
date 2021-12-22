package com.app.RestaurantApp.table;

import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.security.auth.JwtAuthenticationRequest;
import com.app.RestaurantApp.table.dto.TableAdminDTO;
import com.app.RestaurantApp.table.dto.TableCreateDTO;
import com.app.RestaurantApp.table.dto.TableUpdateDTO;
import com.app.RestaurantApp.table.dto.TableWaiterDTO;
import com.app.RestaurantApp.users.appUser.AppUserRepository;
import com.app.RestaurantApp.users.dto.AppUserAdminUserListDTO;
import com.app.RestaurantApp.users.dto.UpdateUserDTO;
import com.app.RestaurantApp.users.dto.UserTokenState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Objects;

import static com.app.RestaurantApp.users.appUser.Constants.ADMIN_PASSWORD;
import static com.app.RestaurantApp.users.appUser.Constants.ADMIN_USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TableControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    private static HttpHeaders headers;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    static void setup(@Autowired TestRestTemplate testRestTemplate){
        ResponseEntity<UserTokenState> responseEntity =
                testRestTemplate.postForEntity("/api/users/login",
                        new JwtAuthenticationRequest(ADMIN_USERNAME, ADMIN_PASSWORD),
                        UserTokenState.class);

        String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();

        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
    }

    @Test
    public void testGetAllTablesFromFloorAdmin(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<TableAdminDTO[]> responseEntity = restTemplate
                .exchange("/api/tables/0", HttpMethod.GET, httpEntity, TableAdminDTO[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(12, responseEntity.getBody().length);
        assertThat(responseEntity.getBody()).extracting("active").containsOnly(true);
        assertThat(responseEntity.getBody()).extracting("floor").containsOnly(0);
    }

    @Test @Transactional
    public void testCreateTable() throws Exception{
        TableCreateDTO tableCreateDTO = new TableCreateDTO();
        tableCreateDTO.setFloor(1);
        tableCreateDTO.setY(1);
        tableCreateDTO.setX(1);
        String json = mapper.writeValueAsString(tableCreateDTO);

        mockMvc.perform(post("/api/tables")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Table created successfully"));
    }

    @Test @Transactional
    public void testCreateTable_NoMoreSpaceForTables() throws Exception{
        TableCreateDTO tableCreateDTO = new TableCreateDTO();
        tableCreateDTO.setFloor(0);
        tableCreateDTO.setY(1);
        tableCreateDTO.setX(1);
        String json = mapper.writeValueAsString(tableCreateDTO);

        mockMvc.perform(post("/api/tables")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Can't add table! Too many tables on floor, limit is 10"));
    }

    @Test @Transactional
    public void testUpdateTable() throws Exception{
        TableUpdateDTO tableUpdateDTO = new TableUpdateDTO();
        tableUpdateDTO.setId(1L);
        tableUpdateDTO.setY(1);
        tableUpdateDTO.setX(1);
        String json = mapper.writeValueAsString(tableUpdateDTO);

        mockMvc.perform(put("/api/tables")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Table 1 updated!"));
    }

    @Test @Transactional
    public void testDeleteTable() throws Exception{
        mockMvc.perform(delete("/api/tables/2")
                .headers(headers))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Table deleted successfully"));
    }

    @Test
    public void testGetTablesFromFloor(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(loginAsWaiter());

        ResponseEntity<TableWaiterDTO[]> responseEntity = restTemplate
                .exchange("/api/tables/getAll?floor=0", HttpMethod.GET, httpEntity, TableWaiterDTO[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(12, responseEntity.getBody().length);
    }

    private HttpHeaders loginAsWaiter(){
        ResponseEntity<UserTokenState> responseEntity =
                restTemplate.postForEntity("/api/users/login",
                        new JwtAuthenticationRequest("waiter@maildrop.cc", "waiter"),
                        UserTokenState.class);

        String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + accessToken);
        return header;
    }
}
