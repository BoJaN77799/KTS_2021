package com.app.RestaurantApp.table;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<Table, Long> {

    List<Table> findByFloorAndActive(int floor, boolean active);

    long countByFloorAndActive(int floor, boolean active);

}
