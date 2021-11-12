package com.app.RestaurantApp.salary;

import java.util.List;

public interface SalaryService {

    List<SalaryDTO> getSalariesOfEmployee(String email);

}
