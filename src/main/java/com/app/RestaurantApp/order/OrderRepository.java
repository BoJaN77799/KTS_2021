package com.app.RestaurantApp.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o join fetch o.orderItems i where o.id =?1")
    public Order findOneWithOrderItems(Long orderId);

    @Query("select o from Order o join fetch o.orderItems i where o.id =?1 and i.status = 'ORDERED'")
    public Order findOneWithOrderItemsForUpdate(Long orderId);

}
