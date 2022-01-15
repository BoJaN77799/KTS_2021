package com.app.RestaurantApp.users.employee;


import java.util.List;
import com.app.RestaurantApp.users.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Employee> searchEmployeesManager(String searchField, String userType, Pageable pageable) {
        if (searchField == null)
            searchField = "";

        if (userType == null){
            userType = "";
        }else if (userType.equalsIgnoreCase("all")){
            userType = "";
        }
        return employeeRepository.searchEmployeesManager(searchField, userType, pageable);
    }

    @Override
    public Employee findEmployeeWithBonuses(String email) {
        return employeeRepository.findEmployeeWithBonuses(email);
    }

    @Override
    public Employee findEmployeeWithSalaries(String email) {
        return employeeRepository.findEmployeeWithSalaries(email);
    }

    @Override
    public List<Employee> findAllEmployeesWithSalariesAndBonuses(boolean deleted) {
        return employeeRepository.findAllEmployeesWithSalariesAndBonuses(deleted);
    }

    @Override
    public Page<Employee> findAllEmployees(Pageable pageable) {
        return employeeRepository.findAllEmployees(pageable);
    }
}
