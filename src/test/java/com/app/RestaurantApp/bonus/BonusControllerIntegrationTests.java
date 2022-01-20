package com.app.RestaurantApp.bonus;

import com.app.RestaurantApp.bonus.dto.BonusDTO;
import com.app.RestaurantApp.security.auth.JwtAuthenticationRequest;
import com.app.RestaurantApp.users.UserException;
import com.app.RestaurantApp.users.dto.UserTokenState;
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

import static com.app.RestaurantApp.bonus.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BonusControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BonusService bonusService;

    @Autowired
    private BonusRepository bonusRepository;

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
    public void testGetBonusesOfEmployee_Valid() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<BonusDTO[]> entity = restTemplate
                .exchange("/api/bonuses/getBonusesOfEmployee/headcook@maildrop.cc",
                        HttpMethod.GET, httpEntity, BonusDTO[].class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());

        List<BonusDTO> bonuses = Arrays.stream(Objects.requireNonNull(entity.getBody())).toList();
        assertEquals(4, bonuses.size());
    }

    @Test
    public void testGetBonusesOfEmployee_NotFound() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<BonusDTO[]> entity = restTemplate
                .exchange("/api/bonuses/getBonusesOfEmployee/cook@maildrop.cc",
                        HttpMethod.GET, httpEntity, BonusDTO[].class);
        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());

        List<BonusDTO> bonuses = Arrays.stream(Objects.requireNonNull(entity.getBody())).toList();
        assertEquals(0, bonuses.size());
    }

    @Test
    public void testGetBonusesOfEmployee_UserException() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<UserException> entity = restTemplate
                .exchange("/api/bonuses/getBonusesOfEmployee/unknown@maildrop.cc",
                        HttpMethod.GET, httpEntity, UserException.class);

        assertEquals(HttpStatus.BAD_REQUEST, entity.getStatusCode());
    }

    @Test
    @Transactional
    public void testCreateBonus_Valid() throws Exception {
        int bonusesSize = bonusRepository.findAll().size();
        BonusDTO bonusDTO = new BonusDTO();
        bonusDTO.setEmail("headcook@maildrop.cc");
        bonusDTO.setAmount(10000);
        bonusDTO.setDate("23.04.2000");

        mockMvc.perform(post("/api/bonuses/createBonus")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bonusDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value("Bonus added successfully!"));

        assertEquals(bonusesSize + 1, bonusRepository.findAll().size());
    }

    @Test
    @Transactional
    public void testCreateBonus_BonusException() throws Exception {
        BonusDTO bonusDTO = new BonusDTO();
        bonusDTO.setEmail("headcook@maildrop.cc");
        bonusDTO.setAmount(-10000);
        bonusDTO.setDate("23.04.2000");

        mockMvc.perform(post("/api/bonuses/createBonus")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bonusDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Bonus can not be negative number or zero!"));
    }


    @Test
    @Transactional
    public void testCreateBonus_UserException() throws Exception {
        BonusDTO bonusDTO = new BonusDTO();
        bonusDTO.setEmail("unknown@maildrop.cc");
        bonusDTO.setAmount(10000);
        bonusDTO.setDate("23.04.2000");

        mockMvc.perform(post("/api/bonuses/createBonus")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(bonusDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Invalid employee, email not found!"));
    }
}
