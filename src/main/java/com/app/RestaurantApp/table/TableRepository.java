package com.app.RestaurantApp.table;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {

    List<Table> findByFloorAndActive(int floor, boolean active);

    long countByFloorAndActive(int floor, boolean active);

    @Query("select distinct t from Table t join fetch t.orders o join fetch o.orderItems" +
            " where t.active=true and t.floor=?1 and o.status <> 'FINISHED'")
    List<Table> findByFloorAndInProgressOrders(int floor); //ako sto ima bar jednu porudzbinu koja nije finished

    @Query("select distinct t from Table t left join fetch t.orders o left join o.orderItems" +
            " where t.active=true and t.floor=?1 and (o.id is null or o.status = 'FINISHED')")
    List<Table> findByFloorAndNoInProgressOrders(int floor); //ako sto nema porudzbina ili su sve finished
}
