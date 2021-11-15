package com.app.RestaurantApp.salary;

import com.app.RestaurantApp.salary.dto.SalaryDTO;

import java.util.List;

public interface SalaryService {

    List<SalaryDTO> getSalariesOfEmployee(String email);

    SalaryDTO createSalary(SalaryDTO salaryDTO) throws SalaryException;
}
