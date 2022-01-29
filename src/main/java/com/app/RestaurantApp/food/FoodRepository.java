package com.app.RestaurantApp.food;

import org.springframework.data.domain.Page;
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
            "and (:type = 'ALL' or lower(f.type) = lower(:type)) " +
            "and (lower(f.menu) = lower(:menu)) " )
    Page<Food> findAllWithPriceByCriteria(@Param("name") String name, @Param("category") String category,
                                          @Param("type") String type, @Param("menu") String menu, Pageable pageable);

    @Query("select distinct f.category.name from Food f")
    List<String> findAllFoodCategories();
}
