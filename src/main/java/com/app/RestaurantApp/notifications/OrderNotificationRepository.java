package com.app.RestaurantApp.notifications;

import com.app.RestaurantApp.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface OrderNotificationRepository extends JpaRepository<OrderNotification, Long> {

    @Modifying
    @Transactional
    void deleteAllByOrder(Order order);

    @Query("select orderNotif from OrderNotification orderNotif join fetch orderNotif.order o where orderNotif.seen = false and orderNotif.employee.id = ?1")
    List<OrderNotification> findAllByEmployeeNotSeen(Long employeeId);

    @Modifying
    @Query("UPDATE OrderNotification orderNotif SET orderNotif.seen = true WHERE orderNotif.employee.id = ?1")
    public void setSeenAllByEmployee(Long employeeId);
}
