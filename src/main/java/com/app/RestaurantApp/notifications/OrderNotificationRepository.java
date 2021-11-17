package com.app.RestaurantApp.notifications;

import com.app.RestaurantApp.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface OrderNotificationRepository extends JpaRepository<OrderNotification, Long> {

    @Modifying
    @Transactional
    void deleteAllByOrder(Order order);

}
