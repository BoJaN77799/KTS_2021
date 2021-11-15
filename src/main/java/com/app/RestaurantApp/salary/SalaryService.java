package com.app.RestaurantApp.salary;

import com.app.RestaurantApp.salary.dto.SalaryDTO;
import com.app.RestaurantApp.users.UserException;

import java.util.List;

public interface SalaryService {

    List<SalaryDTO> getSalariesOfEmployee(String email) throws UserException;

    SalaryDTO createSalary(SalaryDTO salaryDTO) throws SalaryException, UserException;
}
