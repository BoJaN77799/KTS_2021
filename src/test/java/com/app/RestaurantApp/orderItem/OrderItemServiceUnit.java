package com.app.RestaurantApp.orderItem;

import com.app.RestaurantApp.enums.OrderItemStatus;
import com.app.RestaurantApp.notifications.OrderNotificationService;
import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.order.OrderRepository;
import com.app.RestaurantApp.order.OrderService;
import com.app.RestaurantApp.orderItem.dto.OrderItemChangeStatusDTO;
import com.app.RestaurantApp.users.UserException;
import com.app.RestaurantApp.websocket.WebSocketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith({SpringExtension.class})
@SpringBootTest
public class OrderItemServiceUnit {

    @Autowired
    private OrderItemService orderItemService;

    @MockBean
    private OrderItemRepository orderItemRepositoryMock;

    @MockBean
    private OrderNotificationService orderNotificationService;

    @MockBean
    private WebSocketService webSocketService;

    @Test
    public void testDeliverOrderItemTest() throws OrderItemException{
        OrderItem oi = new OrderItem();
        oi.setStatus(OrderItemStatus.FINISHED);

        doReturn(Optional.of(oi)).when(orderItemRepositoryMock).findById(1L);
        orderItemService.deliverOrderItem(1L);
        assertEquals(OrderItemStatus.DELIVERED, oi.getStatus());
        verify(orderItemRepositoryMock).save(oi);
    }

    @Test
    public void testDeliverOrderItemTest_noOrderItem(){
        doReturn(Optional.empty()).when(orderItemRepositoryMock).findById(1L);

        OrderItemException exception = assertThrows(OrderItemException.class, ()->{
            orderItemService.deliverOrderItem(1L);
        });
        assertEquals(exception.getMessage(), "No order item with that id exists!");

    }

    @Test
    public void testDeliverOrderItemTest_orderItemNotFinished(){
        OrderItem oi = new OrderItem();
        oi.setStatus(OrderItemStatus.IN_PROGRESS);
        doReturn(Optional.of(oi)).when(orderItemRepositoryMock).findById(1L);

        OrderItemException exception = assertThrows(OrderItemException.class, ()->{
            orderItemService.deliverOrderItem(1L);
        });
        assertEquals(exception.getMessage(), "Can't deliver unfinished order!");

    }
}
