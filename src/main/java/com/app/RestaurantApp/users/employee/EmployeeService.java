package com.app.RestaurantApp.users.employee;

import java.util.List;
import com.app.RestaurantApp.users.UserException;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

    List<Employee> findAll();

    Employee findByEmail(String email);
  
    Employee saveEmployee(Employee employee);

    Employee findById(Long id);

    List<Employee> findByDeleted(boolean deleted);

    List<Employee> searchEmployeesManager(String searchField, String userType, Pageable pageable);

    Employee findEmployeeWithBonuses(String email);

    Employee findEmployeeWithSalaries(String email);

    List<Employee> findAllEmployeesWithSalariesAndBonuses(boolean deleted);
}

