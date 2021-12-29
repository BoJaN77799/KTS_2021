package com.app.RestaurantApp.order;

import com.app.RestaurantApp.enums.OrderStatus;
import com.app.RestaurantApp.notifications.OrderNotificationRepository;
import com.app.RestaurantApp.order.dto.OrderDTO;
import com.app.RestaurantApp.order.dto.OrderFindDTO;
import com.app.RestaurantApp.order.dto.SimpleOrderDTO;
import com.app.RestaurantApp.orderItem.OrderItemRepository;
import com.app.RestaurantApp.orderItem.dto.OrderItemOrderCreationDTO;
import com.app.RestaurantApp.security.auth.JwtAuthenticationRequest;
import com.app.RestaurantApp.users.dto.UserTokenState;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static com.app.RestaurantApp.order.Constants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private final HttpHeaders headers = new HttpHeaders();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderNotificationRepository orderNotificationRepository;

    private final ObjectMapper mapper = new ObjectMapper();

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
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<SimpleOrderDTO[]> entity = restTemplate
                .exchange("/api/orders/search?page=0&sort=id,DESC&searchField=Dodik&orderStatus=IN_PROGRESS&size=4", HttpMethod.GET, httpEntity, SimpleOrderDTO[].class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        List<SimpleOrderDTO> orders = Arrays.stream(Objects.requireNonNull(entity.getBody())).toList();

        assertNotNull(orders);
        assertEquals(4, orders.size());
        assertNotNull(orders.stream().filter(order -> order.getStatus().equals(OrderStatus.IN_PROGRESS.toString())).findAny().orElse(null));
    }

    @Test
    public void testFindOneWithFood_OK(){
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        // Test invoke
        ResponseEntity<OrderFindDTO> entity = restTemplate
                .exchange("/api/orders/forCook/1", HttpMethod.GET, httpEntity, OrderFindDTO.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        OrderFindDTO order = entity.getBody();

        // Verifying
        assertNotNull(order);
        assertEquals(2, order.getOrderItems().size());
        assertNotNull(order.getOrderItems().stream().filter(orderItem -> orderItem.getItem().getName().equals(ORDER_ITEM_FOOD_NAME_1)).findAny().orElse(null));
        assertNotNull(order.getOrderItems().stream().filter(orderItem -> orderItem.getItem().getName().equals(ORDER_ITEM_FOOD_NAME_2)).findAny().orElse(null));

    }

    @Test
    public void testFindOneWithFood_NOT_FOUND(){
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        // Test invoke
        ResponseEntity<OrderFindDTO> entity = restTemplate
                .exchange("/api/orders/forCook/-1", HttpMethod.GET, httpEntity, OrderFindDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());

        // Verifying
        assertNull(entity.getBody());
    }

    @Test
    public void testFindAllNewWithFood_OK() {
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        // Test invoke
        ResponseEntity<OrderFindDTO[]> entity = restTemplate
                .exchange("/api/orders/forCook/all?page=0&sort=id,DESC&size=4", HttpMethod.GET, httpEntity, OrderFindDTO[].class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        List<OrderFindDTO> orders =  Arrays.stream(Objects.requireNonNull(entity.getBody())).toList();

        // Verifying
        assertNotNull(orders);
        assertEquals(2, orders.stream().toList().size());
    }

    @Test
    public void testFindAllNewWithFood_NOT_FOUND() {
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        // Test invoke
        ResponseEntity<OrderFindDTO[]> entity = restTemplate
                .exchange("/api/orders/forCook/all?page=1&sort=id,DESC&size=4", HttpMethod.GET, httpEntity, OrderFindDTO[].class);

        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());

        // Verifying
        assertEquals(0, Objects.requireNonNull(entity.getBody()).length);
    }

    @Test
    public void testFindAllMyWithFood_OK() {
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        // Test invoke
        ResponseEntity<OrderFindDTO[]> entity = restTemplate
                .exchange("/api/orders/forCook/all/4?page=0&sort=id,DESC&size=4", HttpMethod.GET, httpEntity, OrderFindDTO[].class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        List<OrderFindDTO> orders =  Arrays.stream(Objects.requireNonNull(entity.getBody())).toList();

        // Verifying
        assertNotNull(orders);
        assertEquals(4, orders.stream().toList().size());
    }

    @Test
    public void testFindAllMyWithFood_NOT_FOUND() {
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        // Test invoke
        ResponseEntity<OrderFindDTO[]> entity = restTemplate
                .exchange("/api/orders/forCook/all/4?page=1&sort=id,DESC&size=4", HttpMethod.GET, httpEntity, OrderFindDTO[].class);

        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());

        // Verifying
        assertEquals(0, Objects.requireNonNull(entity.getBody()).length);
    }

    @Test
    public void testFindOneWithDrinks_OK() {
        logInAsBarman();
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        // Test invoke
        ResponseEntity<OrderFindDTO> entity = restTemplate
                .exchange("/api/orders/forBarman/2", HttpMethod.GET, httpEntity, OrderFindDTO.class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        OrderFindDTO order = entity.getBody();

        // Verifying
        assertNotNull(order);
        assertEquals(2, order.getOrderItems().size());
    }

    @Test
    public void testFindOneWithDrinks_NOT_FOUND() {
        logInAsBarman();
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        // Test invoke
        ResponseEntity<OrderFindDTO> entity = restTemplate
                .exchange("/api/orders/forBarman/-1", HttpMethod.GET, httpEntity, OrderFindDTO.class);

        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());

        // Verifying
        assertNull(entity.getBody());
    }

    @Test
    public void testFindAllNewWithDrinks_OK() {
        logInAsBarman();
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        // Test invoke
        ResponseEntity<OrderFindDTO[]> entity = restTemplate
                .exchange("/api/orders/forBarman/all?page=0&sort=id,DESC&size=4", HttpMethod.GET, httpEntity, OrderFindDTO[].class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        List<OrderFindDTO> orders = Arrays.stream(Objects.requireNonNull(entity.getBody())).toList();

        // Verifying
        assertNotNull(orders);
        assertEquals(2, orders.stream().toList().size());
    }

    @Test
    public void testFindAllNewWithDrinks_NOT_FOUND() {
        logInAsBarman();
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        // Test invoke
        ResponseEntity<OrderFindDTO[]> entity = restTemplate
                .exchange("/api/orders/forBarman/all?page=1&sort=id,DESC&size=4", HttpMethod.GET, httpEntity, OrderFindDTO[].class);

        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());

        // Verifying
        assertEquals(0, Objects.requireNonNull(entity.getBody()).length);
    }

    @Test
    public void testFindAllMyWithDrinks_OK() {
        logInAsBarman();
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        // Test invoke
        ResponseEntity<OrderFindDTO[]> entity = restTemplate
                .exchange("/api/orders/forBarman/all/5?page=0&sort=id,DESC&size=10", HttpMethod.GET, httpEntity, OrderFindDTO[].class);

        assertEquals(HttpStatus.OK, entity.getStatusCode());

        List<OrderFindDTO> orders = Arrays.stream(Objects.requireNonNull(entity.getBody())).toList();

        // Verifying
        assertNotNull(orders);
        assertEquals(3, orders.stream().toList().size());
    }

    @Test
    public void testFindAllMyWithDrinks_NOT_FOUND() {
        logInAsBarman();
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);

        // Test invoke
        ResponseEntity<OrderFindDTO[]> entity = restTemplate
                .exchange("/api/orders/forBarman/all/5?page=1&sort=id,DESC&size=10", HttpMethod.GET, httpEntity, OrderFindDTO[].class);

        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());

        // Verifying
        assertEquals(0, Objects.requireNonNull(entity.getBody()).length);
    }

    @Test @Transactional
    public void testCreateOrder() throws Exception {
        logInAsWaiter();
        int ordersSize = orderRepository.findAll().size();
        int orderItemsSize = orderItemRepository.findAll().size();

        OrderDTO orderDTO = createOrderDTOWithOrderItems();
        String jsonOrderDTO = mapper.writeValueAsString(orderDTO);

        mockMvc.perform(post("/api/orders")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonOrderDTO))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(ORDER_CREATED));

        assertEquals(ordersSize + 1, orderRepository.findAll().size());
        assertEquals(orderItemsSize + 2, orderItemRepository.findAll().size());
    }

    @Test @Transactional
    public void testUpdateOrder_ChangeQuantityAndPriority() throws Exception {
        logInAsWaiter();
        int notificationsSize = orderItemRepository.findAll().size();

        OrderDTO orderDTO = createOrderDTOItemUpdate(1L, 1L);
        String jsonOrderDTO = mapper.writeValueAsString(orderDTO);

        mockMvc.perform(put("/api/orders")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonOrderDTO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(ORDER_UPDATED));

        assertEquals(notificationsSize, orderItemRepository.findAll().size());
    }

    @Test @Transactional
    public void testUpdateOrder_ChangeQuantityAndPriorityInProgress() throws Exception {
        logInAsWaiter();
        int notificationsSize = orderNotificationRepository.findAll().size();

        OrderDTO orderDTO = createOrderDTOItemUpdate(3L, 5L);
        String jsonOrderDTO = mapper.writeValueAsString(orderDTO);

        mockMvc.perform(put("/api/orders")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonOrderDTO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(ORDER_UPDATED));

        assertEquals(notificationsSize + 1, orderNotificationRepository.findAll().size());
    }

    @Test @Transactional
    public void testUpdateOrder_Delete() throws Exception {
        logInAsWaiter();
        int notificationsSize = orderNotificationRepository.findAll().size();
        int orderItemsSize = orderItemRepository.findAll().size();
        OrderDTO orderDTO = createOrderDTOItemUpdate(3L, 5L);
        orderDTO.getOrderItems().get(0).setQuantity(DELETE_QUANTITY);
        String jsonOrderDTO = mapper.writeValueAsString(orderDTO);

        mockMvc.perform(put("/api/orders")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonOrderDTO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(ORDER_UPDATED));

        assertEquals(notificationsSize + 1, orderNotificationRepository.findAll().size());
        assertEquals(orderItemsSize - 1, orderItemRepository.findAll().size());
    }

    @Test @Transactional
    public void testUpdateOrder_AddItems() throws Exception {
        logInAsWaiter();
        int notificationsSize = orderNotificationRepository.findAll().size();
        int orderItemsSize = orderItemRepository.findAll().size();
        OrderDTO orderDTO = createOrderDTOItemsAdd(3L);
        String jsonOrderDTO = mapper.writeValueAsString(orderDTO);

        mockMvc.perform(put("/api/orders")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonOrderDTO))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(ORDER_UPDATED));

        assertEquals(notificationsSize + 2, orderNotificationRepository.findAll().size());
        assertEquals(orderItemsSize + 2, orderItemRepository.findAll().size());

    }

    @Test @Transactional
    public void testFinishOrder() throws Exception {
        logInAsWaiter();
        int notificationsSize = orderNotificationRepository.findAll().size();

        mockMvc.perform(put("/api/orders/finish/12")
                .headers(headers))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(ORDER_FINISHED));

        assertEquals(notificationsSize - 2, orderNotificationRepository.findAll().size());
    }

    @Test @Transactional
    public void testFinishOrder_NotFound() throws Exception {
        logInAsWaiter();
        int notificationsSize = orderNotificationRepository.findAll().size();

        mockMvc.perform(put("/api/orders/finish/122")
                        .headers(headers))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value(ORDER_NOT_FOUND));
    }

    private void logInAsWaiter() {
        ResponseEntity<UserTokenState> responseEntity =
                restTemplate.postForEntity("/api/users/login",
                        new JwtAuthenticationRequest(WAITER_EMAIL, WAITER_PWD),
                        UserTokenState.class);

        String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();

        headers.set("Authorization", "Bearer " + accessToken);
    }

    private void logInAsBarman() {
        ResponseEntity<UserTokenState> responseEntity =
                restTemplate.postForEntity("/api/users/login",
                        new JwtAuthenticationRequest(BARMAN_EMAIL, BARMAN_PWD),
                        UserTokenState.class);

        String accessToken = Objects.requireNonNull(responseEntity.getBody()).getAccessToken();

        headers.set("Authorization", "Bearer " + accessToken);
    }

    private OrderDTO createOrderDTOWithOrderItems() {
        OrderDTO orderDTO = createBlankOrderDTO();

        OrderItemOrderCreationDTO oi1DTO = new OrderItemOrderCreationDTO(null, 1L, INIT_QUANTITY1, PRICE1, 1);
        OrderItemOrderCreationDTO oi2DTO = new OrderItemOrderCreationDTO(null, 2L, INIT_QUANTITY1, PRICE2, 2);
        List<OrderItemOrderCreationDTO> ois = new ArrayList<>();
        ois.add(oi1DTO);
        ois.add(oi2DTO);
        orderDTO.setOrderItems(ois);

        return orderDTO;
    }

    private OrderDTO createBlankOrderDTO() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(null);
        orderDTO.setWaiterId(3L);
        orderDTO.setTableId(121L);

        orderDTO.setCreatedAt(null);
        orderDTO.setStatus(null);
        orderDTO.setNote(NOTE);

        orderDTO.setOrderItems(new ArrayList<>());

        return orderDTO;
    }

    private OrderDTO createOrderDTOItemUpdate(Long orderId, Long itemId) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(orderId);
        orderDTO.setNote("Alora, ciao bella.");
        orderDTO.setOrderItems(new ArrayList<>());
        orderDTO.getOrderItems().add(createOrderItemDTOForUpdate(itemId, INIT_QUANTITY1, 1));

        return orderDTO;
    }

    private OrderItemOrderCreationDTO createOrderItemDTOForUpdate(Long id, int quantity, int priority) {
        OrderItemOrderCreationDTO oi = new OrderItemOrderCreationDTO();
        oi.setId(id);
        oi.setQuantity(quantity);
        oi.setPriority(priority);

        return oi;
    }

    private OrderDTO createOrderDTOItemsAdd(Long id) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(id);
        orderDTO.setNote("Alora, ciao bella.");
        orderDTO.setOrderItems(new ArrayList<>());
        orderDTO.getOrderItems().add(createOrderItemDTOForAdding(5L, INIT_QUANTITY1, -1));
        orderDTO.getOrderItems().add(createOrderItemDTOForAdding(6L, INIT_QUANTITY2, -1));

        return orderDTO;
    }

    private OrderItemOrderCreationDTO createOrderItemDTOForAdding(Long id, int quantity, int priority) {
        OrderItemOrderCreationDTO oi = new OrderItemOrderCreationDTO();
        oi.setId(null);
        oi.setItemId(id);
        oi.setQuantity(quantity);
        oi.setPriority(priority);

        return oi;
    }

}
