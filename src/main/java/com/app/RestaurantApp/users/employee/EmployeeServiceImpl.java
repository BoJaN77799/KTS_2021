package com.app.RestaurantApp.users.employee;

import com.app.RestaurantApp.users.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    EmployeeRepository employeeRepository;

    public void createEmployee(Employee employee) {
        // ovde nema provera, jer se provera radi u prethodnoj pozivajucoj fji
        // (createUser iz appUserService)
        employeeRepository.save(employee);
    }
}
