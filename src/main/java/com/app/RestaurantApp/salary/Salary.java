package com.app.RestaurantApp.salary;

import com.app.RestaurantApp.users.employee.Employee;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Entity
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "date_from", nullable = false)
    private Long dateFrom;

    @ManyToOne
    @JoinColumn(name="employee_id", nullable=false)
    private Employee employee;

    public Salary() {
    }

    public Salary(SalaryDTO salaryDTO){
        this.amount = salaryDTO.getAmount();
        this.dateFrom = LocalDate.parse(salaryDTO.getDateFrom(), DateTimeFormatter.ofPattern("dd.MM.yyyy."))
                .atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Long dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

}
