package com.app.RestaurantApp.order;

import com.app.RestaurantApp.enums.OrderStatus;
import com.app.RestaurantApp.order.dto.SimpleOrderDTO;
import com.app.RestaurantApp.security.auth.JwtAuthenticationRequest;
import com.app.RestaurantApp.users.dto.UserTokenState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static com.app.RestaurantApp.order.Constants.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private final HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    void setup(){
        ResponseEntity<UserTokenState> responseEntity =
                restTemplate.postForEntity("/api/users/login",
                        new JwtAuthenticationRequest(COOK_EMAIL, COOK_PWD),
                        UserTokenState.class);

        String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();
        headers.add("Authorization", "Bearer " + accessToken);
    }

    @Test
    public void testSearchOrders() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<SimpleOrderDTO[]> entity = restTemplate
                .exchange("/api/orders/search?page=0&sort=id,DESC&searchField=Dodik&orderStatus=IN_PROGRESS&size=4", HttpMethod.GET, httpEntity, SimpleOrderDTO[].class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        List<SimpleOrderDTO> orders = Arrays.stream(Objects.requireNonNull(entity.getBody())).toList();

        assertNotNull(orders);
        assertEquals(4, orders.size());
        assertNotNull(orders.stream().filter(order -> order.getStatus().equals(OrderStatus.IN_PROGRESS.toString())).findAny().orElse(null));
    }
}
