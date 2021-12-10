package com.app.RestaurantApp.reports;

import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.reports.dto.UserReportDTO;
import com.app.RestaurantApp.security.auth.JwtAuthenticationRequest;
import com.app.RestaurantApp.users.dto.UserTokenState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.*;

import static com.app.RestaurantApp.reports.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportsControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ReportsService reportsService;

    private final HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    void setup(){
        ResponseEntity<UserTokenState> responseEntity =
                restTemplate.postForEntity("/api/users/login",
                        new JwtAuthenticationRequest(MANAGER_EMAIL, MANAGER_PWD),
                        UserTokenState.class);

        String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();
        headers.add("Authorization", "Bearer " + accessToken);
    }

    @Test
    public void testActivityReport() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<UserReportDTO[]> entity = restTemplate
                .exchange("/api/reports/activity/{indicator}", HttpMethod.GET, httpEntity, UserReportDTO[].class, "monthly");

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        List<UserReportDTO> users = Arrays.stream(Objects.requireNonNull(entity.getBody())).toList();

        assertNotNull(users);
        assertEquals(3, users.size());
        assertEquals( 6, users.stream().filter(user -> user.getUserType() == UserType.WAITER).findAny().get().getOrdersAccomplished());
        assertEquals( 3, users.stream().filter(user -> user.getUserType() == UserType.COOK).findAny().get().getOrdersAccomplished());
        assertEquals( 3, users.stream().filter(user -> user.getUserType() == UserType.BARMAN).findAny().get().getOrdersAccomplished());

    }
}
