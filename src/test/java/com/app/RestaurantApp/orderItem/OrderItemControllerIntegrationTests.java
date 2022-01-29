package com.app.RestaurantApp.orderItem;

import com.app.RestaurantApp.enums.OrderItemStatus;
import com.app.RestaurantApp.orderItem.dto.OrderItemChangeStatusDTO;
import com.app.RestaurantApp.security.auth.JwtAuthenticationRequest;
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

import java.util.Objects;

import static com.app.RestaurantApp.order.Constants.WAITER_EMAIL;
import static com.app.RestaurantApp.order.Constants.WAITER_PWD;
import static com.app.RestaurantApp.orderItem.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderItemControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderItemRepository orderItemRepository;

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

    @Test
    @Transactional
    public void testChangeStatus() throws Exception {
        OrderItemChangeStatusDTO oiDTO = new OrderItemChangeStatusDTO(25L, "IN_PROGRESS");
        String jsonOiDTO = mapper.writeValueAsString(oiDTO);
        mockMvc.perform(put("/api/orderItems/changeStatus")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonOiDTO))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").value(OI_SUCC_CHANGED));

        OrderItemStatus oiStatus = orderItemRepository.findById(25L).get().getStatus();
        assertEquals(OrderItemStatus.IN_PROGRESS, oiStatus);
    }

    @Test
    public void testChangeStatus_invalidOrderItem() throws Exception {
        OrderItemChangeStatusDTO oiDTO = new OrderItemChangeStatusDTO(-1L, "IN_PROGRESS");
        String jsonOiDTO = mapper.writeValueAsString(oiDTO);
        mockMvc.perform(put("/api/orderItems/changeStatus")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonOiDTO))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(INVALID_OI_MSG));
    }

    @Test
    public void testChangeStatus_invalidOrderItemStatus() throws Exception {
        OrderItemChangeStatusDTO oiDTO = new OrderItemChangeStatusDTO(25L, "ASDF");
        String jsonOiDTO = mapper.writeValueAsString(oiDTO);
        mockMvc.perform(put("/api/orderItems/changeStatus")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonOiDTO))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(INVALID_OI_STATUS_MSG));
    }

    @Test
    public void testChangeStatus_invalidStatusTransition() throws Exception {
        OrderItemChangeStatusDTO oiDTO = new OrderItemChangeStatusDTO(26L, "IN_PROGRESS");
        String jsonOiDTO = mapper.writeValueAsString(oiDTO);
        mockMvc.perform(put("/api/orderItems/changeStatus")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonOiDTO))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(INVALID_TRANSITION_MSG1));
    }

    @Test
    public void testChangeStatus_higherPriorityAlreadyExists() throws Exception {
        OrderItemChangeStatusDTO oiDTO = new OrderItemChangeStatusDTO(6L, "IN_PROGRESS");
        String jsonOiDTO = mapper.writeValueAsString(oiDTO);
        mockMvc.perform(put("/api/orderItems/changeStatus")
                        .headers(headers)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonOiDTO))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value(PRIORITY_DENIED));
    }
}
