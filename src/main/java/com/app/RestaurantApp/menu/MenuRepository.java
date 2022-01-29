package com.app.RestaurantApp.menu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    Menu findByName(String name);

    @Query("select distinct m from Menu m where m.activeMenu = ?1")
    List<Menu> findAllWithSpecificStatus(boolean activeMenu);
}
