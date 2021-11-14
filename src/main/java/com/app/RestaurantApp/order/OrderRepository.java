package com.app.RestaurantApp.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o join fetch o.orderItems i where o.id =?1")
    Order findOneWithOrderItems(Long orderId);


    @Query("select o from Order o join fetch o.orderItems i where o.id =?1 and i.item.itemType = 'FOOD'")
    Order findOneWithFood(Long id);

    @Query("select distinct o from Order o join fetch o.orderItems i where (o.status = 'NEW' or o.cook is null ) and i.item.itemType = 'FOOD'")
    List<Order> findAllNewWithFood();

    @Query("select distinct o from Order o join fetch o.orderItems i where (o.cook.id =?1 and o.status = 'IN_PROGRESS') and i.item.itemType = 'FOOD'")
    List<Order> findAllMyWithFood(Long id);

    @Query("select o from Order o join fetch o.orderItems i where o.id =?1 and i.item.itemType = 'DRINK'")
    Order findOneWithDrinks(Long id);

    @Query("select distinct o from Order o join fetch o.orderItems i where (o.status = 'NEW' or o.barman is null ) and i.item.itemType = 'DRINK'")
    List<Order> findAllNewWithDrinks();

    @Query("select distinct o from Order o join fetch o.orderItems i where (o.barman.id =?1 and o.status = 'IN_PROGRESS') and i.item.itemType = 'DRINK'")
    List<Order> findAllMyWithDrinks(Long id);
}
