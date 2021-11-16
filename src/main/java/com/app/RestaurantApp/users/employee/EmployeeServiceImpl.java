package com.app.RestaurantApp.users.employee;

import java.util.List;
import com.app.RestaurantApp.users.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    @Override
    public void createEmployee(Employee employee) {
        // ovde nema provera, jer se provera radi u prethodnoj pozivajucoj fji
        // (createUser iz appUserService)
        employeeRepository.save(employee);
    }

    @Override
    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Employee> findByDeleted(boolean deleted) {
        return employeeRepository.findByDeleted(deleted);
    }
}
