package com.app.RestaurantApp.orderItem;

import com.app.RestaurantApp.enums.ItemType;
import com.app.RestaurantApp.enums.OrderItemStatus;
import com.app.RestaurantApp.food.Food;
import com.app.RestaurantApp.notifications.OrderNotificationService;
import com.app.RestaurantApp.order.Order;
import com.app.RestaurantApp.order.OrderException;
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

import java.util.HashSet;
import java.util.Optional;

import static com.app.RestaurantApp.orderItem.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
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
    private OrderNotificationService orderNotificationServiceMock;

    @MockBean
    private OrderService orderServiceMock;

    @MockBean
    private WebSocketService webSocketServiceMock;

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

    @Test
    public void testChangeStatus_invalidOrderItem() {
        given(orderItemRepositoryMock.findById(-1L)).willReturn(Optional.empty());
        OrderItemChangeStatusDTO oiDTO = new OrderItemChangeStatusDTO(-1L, null);

        Exception e = assertThrows(OrderItemException.class, () -> orderItemService.changeStatus(oiDTO));

        assertEquals(e.getMessage(), INVALID_OI_MSG);
    }

    @Test
    public void testChangeStatus_invalidOrderItemStatus() {
        OrderItem oi = new OrderItem();

        given(orderItemRepositoryMock.findById(1L)).willReturn(Optional.of(oi));
        OrderItemChangeStatusDTO oiDTO = new OrderItemChangeStatusDTO(1L, "ASDF");

        Exception e = assertThrows(OrderItemException.class, () -> orderItemService.changeStatus(oiDTO));

        assertEquals(e.getMessage(), INVALID_OI_STATUS_MSG);
    }

    @Test
    public void testChangeStatus_invalidStatusTransition() {
        OrderItem oi = new OrderItem();
        oi.setStatus(OrderItemStatus.FINISHED);

        given(orderItemRepositoryMock.findById(1L)).willReturn(Optional.of(oi));
        OrderItemChangeStatusDTO oiDTO = new OrderItemChangeStatusDTO(1L, "IN_PROGRESS");

        Exception e = assertThrows(OrderItemException.class, () -> orderItemService.changeStatus(oiDTO));

        assertEquals(e.getMessage(), INVALID_TRANSITION_MSG1);
    }

    @Test
    public void testChangeStatus_invalidStatusTransition2() {
        OrderItem oi = new OrderItem();
        oi.setStatus(OrderItemStatus.IN_PROGRESS);

        given(orderItemRepositoryMock.findById(1L)).willReturn(Optional.of(oi));
        OrderItemChangeStatusDTO oiDTO = new OrderItemChangeStatusDTO(1L, "DELIVERED");

        Exception e = assertThrows(OrderItemException.class, () -> orderItemService.changeStatus(oiDTO));

        assertEquals(e.getMessage(), INVALID_TRANSITION_MSG2);
    }

    @Test
    public void testChangeStatus_higherPriorityAlreadyExists() {
        Order order = createOrderWithHighestPriorityOrderItem();
        order.setId(1L);
        OrderItem oi = new OrderItem();
        oi.setStatus(OrderItemStatus.ORDERED);
        oi.setPriority(1); oi.setOrder(order);
        Food food = new Food();
        food.setItemType(ItemType.FOOD);
        oi.setItem(food);

        given(orderItemRepositoryMock.findById(1L)).willReturn(Optional.of(oi));
        given(orderServiceMock.findOneWithOrderItems(1L)).willReturn(order);
        OrderItemChangeStatusDTO oiDTO = new OrderItemChangeStatusDTO(1L, "IN_PROGRESS");

        Exception e = assertThrows(OrderItemException.class, () -> orderItemService.changeStatus(oiDTO));

        assertEquals(PRIORITY_DENIED, e.getMessage());
    }

    @Test
    public void testChangeStatus() throws OrderItemException {
        Order order = createOrderWithLowestPriorityOrderItem();
        order.setId(1L);
        OrderItem oi = new OrderItem();
        oi.setStatus(OrderItemStatus.ORDERED);
        oi.setPriority(1); oi.setOrder(order);
        Food food = new Food();
        food.setItemType(ItemType.FOOD);
        oi.setItem(food);

        given(orderItemRepositoryMock.findById(1L)).willReturn(Optional.of(oi));
        given(orderServiceMock.findOneWithOrderItems(1L)).willReturn(order);
        OrderItemChangeStatusDTO oiDTO = new OrderItemChangeStatusDTO(1L, "IN_PROGRESS");

        OrderItem oiChanged = orderItemService.changeStatus(oiDTO);

        assertEquals(OrderItemStatus.IN_PROGRESS, oi.getStatus());
    }

    private Order createOrderWithHighestPriorityOrderItem() {
        Order order = new Order();
        OrderItem oi = new OrderItem();
        oi.setPriority(2);
        oi.setStatus(OrderItemStatus.ORDERED);
        order.setOrderItems(new HashSet<>());
        order.getOrderItems().add(oi);
        Food food = new Food();
        food.setItemType(ItemType.FOOD);
        oi.setItem(food);

        return order;
    }

    private Order createOrderWithLowestPriorityOrderItem() {
        Order order = new Order();
        OrderItem oi = new OrderItem();
        oi.setPriority(0);
        oi.setStatus(OrderItemStatus.ORDERED);
        order.setOrderItems(new HashSet<>());
        order.getOrderItems().add(oi);

        return order;
    }

}
