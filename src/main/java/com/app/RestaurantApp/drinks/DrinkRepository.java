package com.app.RestaurantApp.drinks;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long> {

    @Query(value = "select d from Drink d " +
            "where (:name = 'ALL' or lower(d.name) like lower(concat('%', :name, '%'))) " +
            "and (:category = 'ALL' or lower(d.category.name) = lower(:category)) " +
            "and (lower(d.menu) = lower(:menu)) " )
    Page<Drink> findAllWithPriceByCriteria(@Param("name") String name, @Param("category") String category,
                                           @Param("menu") String menu, Pageable pageable);

    @Query("select distinct d.category.name from Drink d")
    List<String> findAllDrinkCategories();
}
