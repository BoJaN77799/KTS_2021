package com.app.RestaurantApp.table;

import com.app.RestaurantApp.users.appUser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TableRepository extends JpaRepository<Table, Long>{

    Optional<Table> findByIdAndActive(Long id, boolean active);

    List<Table> findByFloorAndActive(int floor, boolean active);

    long countByFloorAndActive(int floor, boolean active);

    @Query("select distinct t from Table t join fetch t.orders o left join fetch o.orderItems" +
            " where t.active=true and t.floor=?1 and o.status <> 'FINISHED'")
    List<Table> findByFloorAndInProgressOrders(int floor); //ako sto ima bar jednu porudzbinu koja nije finished

    @Query("select distinct t from Table t left join t.orders o" +
            " where t.active=true and t.floor=?1 and (o.id is null or o.status = 'FINISHED')")
    List<Table> findByFloorAndNoInProgressOrders(int floor); //ako sto nema porudzbina ili su sve finished

}
