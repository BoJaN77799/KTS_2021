package com.app.RestaurantApp.users.employee;

import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.item.ItemService;
import com.app.RestaurantApp.item.dto.ItemMenuDTO;
import com.app.RestaurantApp.security.auth.JwtAuthenticationRequest;
import com.app.RestaurantApp.users.dto.AppUserAdminUserListDTO;
import com.app.RestaurantApp.users.dto.EmployeeDTO;
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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.app.RestaurantApp.item.Constants.VALID_MENU;
import static com.app.RestaurantApp.users.employee.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeService itemService;

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
    public void testFindAll() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<EmployeeDTO[]> entity = restTemplate
                .exchange(String.format
                                ("/api/employees/findAll?&page=%s&size=%s&sort=id", 0, 2),
                        HttpMethod.GET, httpEntity, EmployeeDTO[].class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());

        List<EmployeeDTO> employees = Arrays.stream(Objects.requireNonNull(entity.getBody())).toList();
        assertNotNull(employees);
        assertEquals(2, employees.size());
        assertEquals(3L, employees.get(0).getId());
        assertEquals(4L, employees.get(1).getId());

        entity = restTemplate
                .exchange(String.format
                                ("/api/employees/findAll?&page=%s&size=%s&sort=id", 1, 2),
                        HttpMethod.GET, httpEntity, EmployeeDTO[].class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());

        employees = Arrays.stream(Objects.requireNonNull(entity.getBody())).toList();
        assertNotNull(employees);
        assertEquals(2, employees.size());
        assertEquals(5L, employees.get(0).getId());
        assertEquals(6L, employees.get(1).getId());
    }

    @Test
    public void testSearchEmployeesManager() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        //1
        ResponseEntity<EmployeeDTO[]> responseEntity = restTemplate
                .exchange("/api/employees/search_employees?searchField=dod",
                        HttpMethod.GET, httpEntity, EmployeeDTO[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(4, responseEntity.getBody().length);
        assertThat(responseEntity.getBody()).extracting("id")
                .containsExactlyInAnyOrderElementsOf(Arrays.asList(3L, 4L, 5L, 6L));

        //2
        responseEntity = restTemplate.exchange("/api/employees/search_employees?userType=BARMAN",
                HttpMethod.GET, httpEntity, EmployeeDTO[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().length);
        assertThat(responseEntity.getBody()).extracting("userType").containsOnly(UserType.BARMAN.toString());

        //3
        responseEntity = restTemplate.exchange("/api/employees/search_employees?searchField=igor&userType=COOK",
                HttpMethod.GET, httpEntity, EmployeeDTO[].class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().length);
        assertThat(responseEntity.getBody()).extracting("firstName").containsOnly("Igor");
    }
}
