package com.app.RestaurantApp.item;

import com.app.RestaurantApp.orderItem.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i from Item i where  i.id in :ids")
    List<Item> findAllWithIds(@Param("ids")List<Long> ids);

}
