package com.app.RestaurantApp.orderItem;

import com.app.RestaurantApp.enums.OrderItemStatus;
import com.app.RestaurantApp.users.appUser.AppUserService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderItemServiceIntegrationTests {

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
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
}
