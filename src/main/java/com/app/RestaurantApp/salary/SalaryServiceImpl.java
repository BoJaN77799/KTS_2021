package com.app.RestaurantApp.salary;

import com.app.RestaurantApp.salary.dto.SalaryDTO;
import com.app.RestaurantApp.users.UserException;
import com.app.RestaurantApp.users.employee.Employee;
import com.app.RestaurantApp.users.employee.EmployeeRepository;
import com.app.RestaurantApp.users.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    private SalaryRepository salaryRepository;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

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

        Employee e = employeeService.findEmployeeWithSalaries(salaryDTO.getEmail());
        if (e == null) throw new UserException("Invalid employee, email not found!");
        // provjerimo je li postoji ovakav datum da samo azuriram platu
        long dateFrom = LocalDate.parse(salaryDTO.getDateFrom(), DateTimeFormatter.ofPattern("dd.MM.yyyy."))
                .atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();

        Salary salary = checkDateSalaries(e.getSalaries(), dateFrom);
        if (salary == null) {
            salary = new Salary(salaryDTO);
            salary.setEmployee(e);
            e.getSalaries().add(salary);
        } else {
            salary.setAmount(salaryDTO.getAmount());
        }

        e.setSalary(salary.getAmount());

        SalaryUtils.CheckSalaryInfo(salary);

        employeeRepository.save(e);
        //return salaryRepository.save(salary);
        return salary;
    }

    private Salary checkDateSalaries(Set<Salary> salaries, long date) {
        for (Salary s : salaries)
            if (s.getDateFrom() == date)
                return s;
        return null;
    }
}
