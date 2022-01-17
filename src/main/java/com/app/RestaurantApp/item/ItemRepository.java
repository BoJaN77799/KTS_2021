package com.app.RestaurantApp.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i from Item i where  i.id in :ids")
    List<Item> findAllWithIds(@Param("ids")List<Long> ids);

    @Query("select distinct i from Item i left join fetch i.prices where i.id = ?1")
    Item findByIdItemWithPrices(Long id);

    @Query(
            value = "select distinct i from Item i where i.deleted = 'False' and i.menu = ?1",
            countQuery = "select count(distinct i) from Item i where i.deleted ='False' and i.menu =?1"
    )
    Page<Item> findAllItemsWithMenuName(String name, Pageable pageable);
}
