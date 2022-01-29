package com.app.RestaurantApp.orderItem;

import com.app.RestaurantApp.enums.OrderItemStatus;
import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.orderItem.dto.OrderItemChangeStatusDTO;
import com.app.RestaurantApp.users.appUser.AppUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.app.RestaurantApp.orderItem.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderItemServiceIntegrationTests {

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    @Transactional
    public void testDeliverOrderItem() throws OrderItemException {
        try{
            orderItemService.deliverOrderItem(17L);
        }catch(OrderItemException e){
            fail();
        }
    }

    @Test
    public void testDeliverOrderItem_invalidOrder() {
        try {
            orderItemService.deliverOrderItem(99999L);
        }catch (OrderItemException e){
            assertEquals("No order item with that id exists!", e.getMessage());
        }
    }

    @Test
    public void testDeliverOrderItem_invalidOrderToDeliver() {
        try {
            orderItemService.deliverOrderItem(1L);
        }catch (OrderItemException e){
            assertEquals("Can't deliver unfinished order!", e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testChangeStatus() throws OrderItemException {
        OrderItem oi = orderItemService.changeStatus(new OrderItemChangeStatusDTO(25L, "IN_PROGRESS"));

        assertEquals(OrderItemStatus.IN_PROGRESS, oi.getStatus());
    }

    @Test
    public void testChangeStatus_invalidOrderItem() throws OrderItemException {
        try {
            orderItemService.changeStatus(new OrderItemChangeStatusDTO(-1L, "IN_PROGRESS"));
        } catch (OrderItemException e) {
            assertEquals(INVALID_OI_MSG, e.getMessage());
        }
    }

    @Test
    public void testChangeStatus_invalidOrderItemStatus() throws OrderItemException {
        try {
            orderItemService.changeStatus(new OrderItemChangeStatusDTO(25L, "ASDF"));
        } catch (OrderItemException e) {
            assertEquals(INVALID_OI_STATUS_MSG, e.getMessage());
        }
    }

    @Test
    public void testChangeStatus_invalidStatusTransition() throws OrderItemException {
        try {
            orderItemService.changeStatus(new OrderItemChangeStatusDTO(26L, "IN_PROGRESS"));
        } catch (OrderItemException e) {
            assertEquals(INVALID_TRANSITION_MSG1, e.getMessage());
        }
    }

    @Test
    public void testChangeStatus_higherPriorityAlreadyExists() throws OrderItemException {
        try {
            orderItemService.changeStatus(new OrderItemChangeStatusDTO(6L, "IN_PROGRESS"));
        } catch (OrderItemException e) {
            assertEquals(PRIORITY_DENIED, e.getMessage());
        }
    }
}
