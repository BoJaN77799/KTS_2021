package com.app.RestaurantApp.notifications;

import com.app.RestaurantApp.security.auth.JwtAuthenticationRequest;
import com.app.RestaurantApp.users.dto.UserTokenState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.app.RestaurantApp.order.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderNotificationControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private final HttpHeaders headers = new HttpHeaders();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderNotificationRepository orderNotificationRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        ResponseEntity<UserTokenState> responseEntity =
                restTemplate.postForEntity("/api/users/login",
                        new JwtAuthenticationRequest(WAITER_EMAIL, WAITER_PWD),
                        UserTokenState.class);
        String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();
        headers.add("Authorization", "Bearer " + accessToken);
    }

    @Test
    public void testFindAllNotSeenForEmployee() throws Exception {
        mockMvc.perform(get(String.format("/api/orderNotifications/%d", 3L))
                        .headers(headers))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(3L))
                .andExpect(jsonPath("$[1].id").value(4L))
                .andExpect(jsonPath("$[2].id").value(5L));
    }

    @Test
    @Transactional
    public void testSetSeenAllNotificationsForEmployee() throws Exception {
        Long employeeId = 3L;
        mockMvc.perform(put("/api/orderNotifications/setSeenAll")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(employeeId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("All notifications set to seen."));

        List<OrderNotification> orderNotifications = orderNotificationRepository.findAll();
        for (OrderNotification on : orderNotifications) {
            if(on.getEmployee().getId().equals(3L))
                assertTrue(on.isSeen());
        }
    }

    @Test
    public void testSetSeenAllNotificationsForEmployee_InvalidEmployeeId() throws Exception {
        Long employeeId = -1L;

        mockMvc.perform(put("/api/orderNotifications/setSeenAll")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(employeeId)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("There is no employee with given id."));
    }

    @Test
    @Transactional
    public void testSetSeen() throws Exception {
        Long orderNotificationId = 3L;
        mockMvc.perform(put("/api/orderNotifications/setSeenOne")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(orderNotificationId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("Notification set to seen."));

        List<OrderNotification> orderNotifications = orderNotificationRepository.findAll();
        for (OrderNotification on : orderNotifications) {
            if(on.getId().equals(3L))
                assertTrue(on.isSeen());
        }
    }

    @Test
    public void testSetSeen_InvalidId() throws Exception {
        Long orderNotificationId = -1L;
        mockMvc.perform(put("/api/orderNotifications/setSeenOne")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(orderNotificationId)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("Invalid order notification id sent from front!"));
    }

}
