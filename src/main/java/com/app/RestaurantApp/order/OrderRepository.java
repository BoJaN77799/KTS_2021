package com.app.RestaurantApp.order;

import org.springframework.data.domain.Page;
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

    @Query(
            value = "select distinct o from Order o join fetch o.orderItems i where o.status <> 'FINISHED' and (o.status = 'NEW' or o.cook is null ) and i.item.itemType = 'FOOD'",
            countQuery = "select count(distinct o) from Order o join o.orderItems i where o.status <> 'FINISHED' and (o.status = 'NEW' or o.cook is null ) and i.item.itemType = 'FOOD'"
    )
    Page<Order> findAllNewWithFood(Pageable pageable);

    @Query(
            value = "select distinct o from Order o join fetch o.orderItems i where (o.cook.id =?1 and o.status = 'IN_PROGRESS') and i.item.itemType = 'FOOD'",
            countQuery = "select count(distinct o) from Order o join o.orderItems i where (o.cook.id =?1 and o.status = 'IN_PROGRESS') and i.item.itemType = 'FOOD'"
    )
    Page<Order> findAllMyWithFood(Long id, Pageable pageable);

    @Query("select o from Order o join fetch o.orderItems i where o.id =?1 and i.item.itemType = 'DRINK'")
    Order findOneWithDrinks(Long id);

    @Query(
            value = "select distinct o from Order o join fetch o.orderItems i where (o.status = 'NEW' or o.barman is null ) and i.item.itemType = 'DRINK'",
            countQuery = "select count(distinct o) from Order o join o.orderItems i where (o.status = 'NEW' or o.barman is null ) and i.item.itemType = 'DRINK'"
    )
    Page<Order> findAllNewWithDrinks(Pageable pageable);

    @Query(
            value = "select distinct o from Order o join fetch o.orderItems i where (o.barman.id =?1 and o.status = 'IN_PROGRESS') and i.item.itemType = 'DRINK'",
            countQuery = "select count(distinct o) from Order o join o.orderItems i where (o.barman.id =?1 and o.status = 'IN_PROGRESS') and i.item.itemType = 'DRINK'"
    )
    Page<Order> findAllMyWithDrinks(Long id, Pageable pageable);

    @Query("select distinct o from Order o left join fetch o.orderItems i where o.createdAt <= ?2 and o.createdAt >= ?1 and o.status = 'FINISHED'")
    List<Order> findAllOrderInIntervalOfDates(Long dateFrom, Long dateTo);

    @Query(value = "select o from Order o " +
            "WHERE (:search = '' " +
            "or lower(o.waiter.firstName) like lower(concat('%', :search, '%')) " +
            "or lower(o.waiter.lastName) like lower(concat('%', :search, '%'))" +
            "or cast(o.table.id as string) like lower(concat('%', :search, '%')))" +
            "AND (:orderStatus = '' or o.status = :orderStatus)")
    Page<Order> searchOrders(@Param("search") String searchField, @Param("orderStatus") String orderStatus, Pageable pageable);

    @Query("select o from Order o where (o.createdAt >= ?1 and o.createdAt <= ?2) and o.status = 'FINISHED'")
    List<Order> getOrdersByDate(long dateFrom, long dateTo);


    @Query("select o from Order o join fetch o.orderItems oi join fetch oi.item where o.table.id =?1 and o.status <> 'FINISHED'")
    List<Order> findActiveFromTable(Long table_id);

    @Query("select o from Order o where o.table.id =?1 and o.status <> 'FINISHED'")
    List<Order> findActiveOrderByTable(Long table_id);

}
