package com.app.RestaurantApp.users.employee;

import java.util.List;
import com.app.RestaurantApp.users.UserException;

public interface EmployeeService {

    List<Employee> findAll();

    Employee findByEmail(String email);
  
    void createEmployee(Employee employee);

}

