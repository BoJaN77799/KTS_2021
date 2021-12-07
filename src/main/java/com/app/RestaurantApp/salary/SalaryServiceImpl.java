package com.app.RestaurantApp.salary;

import com.app.RestaurantApp.salary.dto.SalaryDTO;
import com.app.RestaurantApp.users.UserException;
import com.app.RestaurantApp.users.employee.Employee;
import com.app.RestaurantApp.users.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

@Service
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private EmployeeService employeeService;

    @Override
    public List<SalaryDTO> getSalariesOfEmployee(String email) throws UserException {
        Employee e = employeeService.findEmployeeWithSalaries(email);
        if (e == null) throw new UserException("Invalid employee, email not found!");

        List<SalaryDTO> salaries = new ArrayList<>();
        Iterator<Salary> it = e.getSalaries().stream().sorted(Comparator.comparingLong(Salary::getDateFrom)).iterator();
        int i = 0;
        while (it.hasNext()) {
            salaries.add(new SalaryDTO(it.next()));
            if (i != 0)
                salaries.get(i - 1).setDateTo(salaries.get(i).getDateFrom());
            ++i;
        }
        return salaries;
    }

    @Override
    public Salary createSalary(SalaryDTO salaryDTO) throws SalaryException, UserException {
        // Postavljam da bude trenutan datum za kreiranje plate
        salaryDTO.setDateFrom(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));

        Employee e = employeeService.findByEmail(salaryDTO.getEmail());
        if (e == null) throw new UserException("Invalid employee, email not found!");
        Salary salary = new Salary(salaryDTO);
        salary.setEmployee(e);

        SalaryUtils.CheckSalaryInfo(salary);

        return salaryRepository.save(salary);
    }

}
