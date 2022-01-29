package com.app.RestaurantApp.orderItem;

import com.app.RestaurantApp.order.dto.OrderFindDTO;
import com.app.RestaurantApp.security.auth.JwtAuthenticationRequest;
import com.app.RestaurantApp.users.appUser.AppUserRepository;
import com.app.RestaurantApp.users.dto.UserTokenState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static com.app.RestaurantApp.order.Constants.WAITER_EMAIL;
import static com.app.RestaurantApp.order.Constants.WAITER_PWD;
import static com.app.RestaurantApp.users.appUser.Constants.ADMIN_PASSWORD;
import static com.app.RestaurantApp.users.appUser.Constants.ADMIN_USERNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderItemControllerIntegrationTests {

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
                        new JwtAuthenticationRequest(WAITER_EMAIL, WAITER_PWD),
                        UserTokenState.class);

        String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();

        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
    }

    @Test
    public void testDeliverOrderItemWaiter(){
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        // Test invoke
        ResponseEntity<String> entity = restTemplate
                .exchange("/api/orderItems/deliver/17", HttpMethod.GET, httpEntity, String.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        String result = entity.getBody();
        assertEquals("Order item delivered!", result);
    }
}
