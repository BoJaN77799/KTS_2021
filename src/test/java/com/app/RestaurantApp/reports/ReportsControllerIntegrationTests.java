package com.app.RestaurantApp.reports;

import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.reports.dto.IncomeExpenses;
import com.app.RestaurantApp.reports.dto.Sales;
import com.app.RestaurantApp.reports.dto.UserReportDTO;
import com.app.RestaurantApp.security.auth.JwtAuthenticationRequest;
import com.app.RestaurantApp.users.dto.UserTokenState;
import org.junit.jupiter.api.BeforeAll;
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

    private static HttpHeaders headers;

    @BeforeAll
    static void setup(@Autowired TestRestTemplate testRestTemplate){
        ResponseEntity<UserTokenState> responseEntity =
                testRestTemplate.postForEntity("/api/users/login",
                        new JwtAuthenticationRequest(MANAGER_EMAIL, MANAGER_PWD),
                        UserTokenState.class);

        String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();
        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
    }

    @Test
    public void testGetReportsSales_OK() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<Sales[]> entity = restTemplate
                .exchange(String.format("/api/reports/getReportsSales/%s-%s", LAST_THREE_MONTHS_STRING,
                        CURRENT_DATE_STRING),
                        HttpMethod.GET, httpEntity, Sales[].class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        List<Sales> sales = Arrays.stream(Objects.requireNonNull(entity.getBody())).toList();
        assertNotNull(sales);
        assertEquals(5, sales.size());

        assertEquals(1L, sales.get(0).getItemId());
        assertEquals(1620.0, sales.get(0).getPriceCount());
        assertEquals(7, sales.get(0).getItemCount());

        assertEquals(2L, sales.get(1).getItemId());
        assertEquals(2400.0, sales.get(1).getPriceCount());
        assertEquals(4, sales.get(1).getItemCount());

        assertEquals(4L, sales.get(2).getItemId());
        assertEquals(27500.0, sales.get(2).getPriceCount());
        assertEquals(11, sales.get(2).getItemCount());

        assertEquals(7L, sales.get(3).getItemId());
        assertEquals(2060.0, sales.get(3).getPriceCount());
        assertEquals(12, sales.get(3).getItemCount());

        assertEquals(8L, sales.get(4).getItemId());
        assertEquals(740.0, sales.get(4).getPriceCount());
        assertEquals(2, sales.get(4).getItemCount());
    }

    @Test
    public void testGetReportsSales_NotFound() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<Sales[]> entity = restTemplate
                .exchange(String.format("/api/reports/getReportsSales/%s-%s", INVALID_DATE_FROM_STRING,
                        INVALID_DATE_TO_STRING),
                        HttpMethod.GET, httpEntity, Sales[].class);

        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());

        List<Sales> sales = Arrays.stream(Objects.requireNonNull(entity.getBody())).toList();
        assertNotNull(sales);
        assertTrue(sales.isEmpty());
    }

    @Test
    public void testGetIncomeExpenses_OK() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<IncomeExpenses> entity = restTemplate
                .exchange(String.format("/api/reports/getIncomeExpenses/%s-%s", LAST_THREE_MONTHS_STRING,
                        CURRENT_DATE_STRING),
                        HttpMethod.GET, httpEntity, IncomeExpenses.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        assertNotNull(entity.getBody());
        assertEquals(996.13, entity.getBody().getExpenses());
        assertEquals(400, entity.getBody().getIncome());
    }

    @Test
    public void testGetIncomeExpenses_NotFound() {
        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<IncomeExpenses> entity = restTemplate
                .exchange(String.format("/api/reports/getIncomeExpenses/%s-%s", INVALID_DATE_FROM_STRING,
                        INVALID_DATE_TO_STRING),
                        HttpMethod.GET, httpEntity, IncomeExpenses.class);

        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());

        assertNotNull(entity.getBody());
        assertTrue(entity.getBody().getExpenses() == 0 && entity.getBody().getIncome() == 0);
    }

    /*
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
    */
}
