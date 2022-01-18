package com.app.RestaurantApp.users.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByEmail(String email);

    @Query("SELECT u FROM Employee u where u.deleted=false and  " +
            "( :search = '' " +
            "or lower(u.firstName) like lower(concat('%', :search, '%')) " +
            "or lower(u.lastName) like lower(concat('%', :search, '%')))" +
            "AND (:userType = '' or u.userType = :userType) ")
    List<Employee> searchEmployeesManager(@Param("search") String searchField, @Param("userType") String userType, Pageable pageable);

    @Query("select distinct e from Employee e left join fetch e.bonuses where e.email = ?1")
    Employee findEmployeeWithBonuses(String email);

    @Query("select distinct e from Employee e left join fetch e.salaries where e.email = ?1")
    Employee findEmployeeWithSalaries(String email);

    @Query("select distinct e from Employee e left join fetch e.salaries left join fetch e.bonuses where e.deleted = ?1")
    List<Employee> findAllEmployeesWithSalariesAndBonuses(boolean deleted);

    @Query(
            value = "select distinct e from Employee e where e.deleted = 'False'",
            countQuery = "select count(distinct e) from Employee e where e.deleted ='False'"
    )
    Page<Employee> findAllEmployees(Pageable pageable);
}
