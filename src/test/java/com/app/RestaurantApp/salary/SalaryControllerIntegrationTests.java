package com.app.RestaurantApp.salary;

import com.app.RestaurantApp.salary.dto.SalaryDTO;
import com.app.RestaurantApp.security.auth.JwtAuthenticationRequest;
import com.app.RestaurantApp.users.dto.UserTokenState;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static com.app.RestaurantApp.salary.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SalaryControllerIntegrationTests {

    private final HttpHeaders headers = new HttpHeaders();

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private SalaryRepository salaryRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        ResponseEntity<UserTokenState> responseEntity =
                restTemplate.postForEntity("/api/users/login", new JwtAuthenticationRequest(MANAGER_EMAIL, MANAGER_PWD),
                        UserTokenState.class);

        String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();
        headers.add("Authorization", "Bearer " + accessToken);
    }

    @Test
    public void testGetSalariesOfEmployee() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<SalaryDTO[]>  entity = restTemplate
                .exchange("/api/salaries/getSalariesOfEmployee/headcook@maildrop.cc",
                        HttpMethod.GET, httpEntity, SalaryDTO[].class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());

        List<SalaryDTO> salaries =  Arrays.stream(Objects.requireNonNull(entity.getBody())).toList();
        assertEquals(3, salaries.size());
    }

    @Test
    @Transactional
    public void testCreateBonus() throws Exception {
        int salariesSize = salaryRepository.findAll().size();
        SalaryDTO salaryDTO = new SalaryDTO();
        salaryDTO.setEmail("headcook@maildrop.cc");
        salaryDTO.setDateFrom("23.04.2020");
        salaryDTO.setAmount(10000);

        mockMvc.perform(post("/api/salaries/createSalary")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(salaryDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Salary added successfully"));

        assertEquals(salariesSize + 1, salaryRepository.findAll().size());
    }

}
