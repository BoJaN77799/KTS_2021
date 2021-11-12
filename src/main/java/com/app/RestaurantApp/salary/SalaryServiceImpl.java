package com.app.RestaurantApp.salary;

import com.app.RestaurantApp.users.employee.Employee;
import com.app.RestaurantApp.users.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public List<SalaryDTO> getSalariesOfEmployee(String email) {
        Employee e = employeeService.findByEmail(email);
        List<SalaryDTO> salaries = new ArrayList<>();
        for (Salary s : e.getSalaries())
            salaries.add(new SalaryDTO(s));
        return salaries;
    }

}
