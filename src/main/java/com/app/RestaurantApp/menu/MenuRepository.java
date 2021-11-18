package com.app.RestaurantApp.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    Menu findByName(String name);

    @Query("select m from Menu m left join fetch m.items where m.name = ?1")
    Menu findByNameWithItems(String name);
}
