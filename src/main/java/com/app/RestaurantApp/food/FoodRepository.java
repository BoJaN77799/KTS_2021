package com.app.RestaurantApp.food;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    @Query(value = "select f from Food f " +
            "where (:name = 'ALL' or lower(f.name) like lower(concat('%', :name, '%'))) " +
            "and (:category = 'ALL' or lower(f.category.name) = lower(:category)) " +
            "and (:type = 'ALL' or lower(f.type) = lower(:type)) ")
    List<Food> findAllByCriteria(@Param("name") String name, @Param("category") String category,
                                 @Param("type") String type, Pageable pageable);
}
