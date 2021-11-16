package com.app.RestaurantApp.order;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("select o from Order o join fetch o.orderItems i where o.id =?1 and i.status = 'ORDERED'")
    Order findOneWithOrderItemsForUpdate(Long orderId);

    @Query(value = "select o from Order o " +
            "WHERE (:search = '' " +
            "or lower(o.waiter.firstName) like lower(concat('%', :search, '%')) " +
            "or lower(o.waiter.lastName) like lower(concat('%', :search, '%'))" +
            "or cast(o.table.id as string) like lower(concat('%', :search, '%')))" +
            "AND (:orderStatus = '' or o.status = :orderStatus)")
    List<Order> searchOrders(@Param("search") String searchField, @Param("orderStatus") String orderStatus, Pageable pageable);

    @Query("select o from Order o where (o.createdAt >= ?1 and o.createdAt <= ?2) and o.status = 'FINISHED'")
    List<Order> getOrdersByDate(long dateFrom, long dateTo);
}
