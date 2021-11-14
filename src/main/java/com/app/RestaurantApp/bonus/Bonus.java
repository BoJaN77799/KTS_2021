package com.app.RestaurantApp.bonus;

import com.app.RestaurantApp.users.employee.Employee;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Entity
public class Bonus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "date_to", nullable = false)
    private Long date;

    @ManyToOne
    @JoinColumn(name="employee_id", nullable=false)
    private Employee employee;

    public Bonus() { }

    public Bonus(BonusDTO bonusDTO){
        this.amount = bonusDTO.getAmount();
        this.date = LocalDate.parse(bonusDTO.getDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy."))
        .atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

}
