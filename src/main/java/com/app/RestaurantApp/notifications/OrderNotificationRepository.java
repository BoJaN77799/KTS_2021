package com.app.RestaurantApp.notifications;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderNotificationRepository extends JpaRepository<OrderNotification, Long> {
}
