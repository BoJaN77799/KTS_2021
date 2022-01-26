package com.app.RestaurantApp.notifications;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class OrderNotificationRepositoryIntegrationTests {

    @Autowired
    private OrderNotificationRepository orderNotificationRepository;

    @Test
    public void testFindAllByEmployeeNotSeen() {
        List<OrderNotification> orderNotifications = orderNotificationRepository.findAllByEmployeeNotSeen(3L);

        assertEquals(4, orderNotifications.size());
        assertFalse(orderNotifications.get(0).isSeen());
        assertFalse(orderNotifications.get(1).isSeen());
        assertFalse(orderNotifications.get(2).isSeen());
        assertFalse(orderNotifications.get(3).isSeen());
    }

    @Test
    public void testFindAllByEmployeeNotSeen2() {
        List<OrderNotification> orderNotifications = orderNotificationRepository.findAllByEmployeeNotSeen(-1L);

        assertEquals(0, orderNotifications.size());
    }

    @Test
    @Transactional
    public void testSetSeenAllByEmployee() {
        orderNotificationRepository.setSeenAllByEmployee(3L);

        assertEquals(0, orderNotificationRepository.findAllByEmployeeNotSeen(3L).size());
    }

}
