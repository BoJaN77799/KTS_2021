package com.app.RestaurantApp.users.employee;

import com.app.RestaurantApp.bonus.Bonus;
import com.app.RestaurantApp.enums.UserType;
import com.app.RestaurantApp.salary.Salary;
import com.app.RestaurantApp.users.appUser.AppUser;

import javax.persistence.*;
import java.util.Set;

@Entity
@DiscriminatorValue(value="E")
public class Employee extends AppUser {

    @Column(name = "salary")
    private double salary;

    @OneToMany(mappedBy="employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Salary> salaries;

    @OneToMany(mappedBy="employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Bonus> bonuses;

    public Employee() {}

    public Employee(AppUser appUser) {
        super(appUser);
        salary = 0;
    }

    public Employee(Long id) {
        super(id);
    }

    public Employee(Long id, String email, String firstName, String lastName, UserType userType) {
        super(id, email, firstName, lastName, userType);
        salary = 0;
    }

    public Employee(Long id, String lastName) {
        super(id, lastName);
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Set<Salary> getSalaries() {
        return salaries;
    }

    public void setSalaries(Set<Salary> salaries) {
        this.salaries = salaries;
    }

    public Set<Bonus> getBonuses() {
        return bonuses;
    }

    public void setBonuses(Set<Bonus> bonuses) {
        this.bonuses = bonuses;
    }

}
