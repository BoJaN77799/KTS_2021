package com.app.RestaurantApp.users.employee;


import java.util.List;

public interface EmployeeService {

    public List<Employee> findAll();

    public Employee findByEmail(String email);
}
