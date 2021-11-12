package com.app.RestaurantApp.users.employee;


import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();

    Employee findByEmail(String email);
}
